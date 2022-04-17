package sebastien.cantin.musicAPI.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import sebastien.cantin.musicAPI.model.Jukebox;
import sebastien.cantin.musicAPI.model.Page;
import sebastien.cantin.musicAPI.model.Setting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sebastien Cantin
 */
public class MusicRestService {

    /**
     * takes a setting id and returns all the jukeboxes in the database that meet the requirements for the given setting
     *
     * @param settingId the id of the setting that we are checking for compatible jukeboxes of
     * @param model a string that filters jukeboxes by their name (optional)
     * @param offset an integer that defines the index of the returned jukeboxes to start at
     * @param limit an integer that defines how many jukeboxes are to be returned per page
     * @return a paginated list of jukeboxes that satisfy the requirements of the given setting
     */
    public static List<Page> getCompatibleJukeboxes(String settingId, String model, String offset, String limit) throws IOException {

        ArrayList<Jukebox> jukeboxes = new ArrayList<>();

        //send get requests to the database
        String jukeboxDB = getRequest(new URL("http://my-json-server.typicode.com/touchtunes/tech-assignment/jukes"));
        String settingDB = getRequest(new URL("http://my-json-server.typicode.com/touchtunes/tech-assignment/settings"));

        //transform the responses to lists of objects
        List<Jukebox> jukeboxList = parseJukeboxResponse(jukeboxDB);
        List<Setting> settingList = parseSettingResponse(settingDB);

        //find correct setting
        Setting correctSetting = null;
        for (Setting s: settingList){
            if (s.getId().equals(settingId)) {
                correctSetting = s;
                break;
            }
        }

        if (correctSetting==null) throw new IllegalArgumentException("Error: "+ HttpStatus.NOT_FOUND+". The given setting ID was not found in the database");

        //go through each jukebox to find all the ones that meet requirements
        jukeboxIteration:
        for (Jukebox j : jukeboxList){
            ArrayList<String> componentsCopy = new ArrayList<String>(j.getComponents());
            for (String requirement: correctSetting.getRequires()){
                boolean b = componentsCopy.remove(requirement);
                if (!b) continue jukeboxIteration;
            }
            jukeboxes.add(j);
        }

        //transform the inputs and error check
        final String finalModel= model;
        int realOffset = 0;
        if (offset!=null && offset.trim().length()!=0) realOffset = Integer.parseInt(offset);
        if (realOffset <= -1) throw new IllegalArgumentException("Error: "+ HttpStatus.NOT_ACCEPTABLE+". The input offset is smaller than 0");
        int realLimit = jukeboxes.size();
        if (limit!=null && limit.trim().length()!=0) realLimit= Integer.parseInt(limit);
        if (realLimit <= 0) throw new IllegalArgumentException("Error: "+ HttpStatus.NOT_ACCEPTABLE+". The input limit is smaller than 1");

        boolean correctModelName = false;
        for (Jukebox j: jukeboxes){
            if (j.getModel().equals(finalModel)) {
                correctModelName=true;
                break;
            }
        }
        if (!correctModelName && finalModel!= null && finalModel.trim().length()!=0) throw new IllegalArgumentException("Error: "+ HttpStatus.NOT_ACCEPTABLE+". There are no jukeboxes in the database with the input model name");

        //filter by name
        if (finalModel!= null && finalModel.trim().length()!=0){
            jukeboxes.removeIf(jukebox -> !jukebox.getModel().equals(finalModel));
        }

        //create paginated list
        ArrayList<Page> jukeboxes1 = new ArrayList<>();
        if (realOffset < jukeboxes.size()) {
            for (int i = realOffset; i < Math.min(realOffset + realLimit, jukeboxes.size()); i++) {
                jukeboxes1.add(new Page(i - realOffset + 1, jukeboxes.get(i)));
            }
        }

        return jukeboxes1;
    }

    /**
     * Transforms a Json string that describes many settings into a List of Setting objects
     *
     * @param response a String with Json formatting that describes Setting objects
     * @return a List of Setting objects
     * @throws JsonProcessingException
     */
    public static List<Setting> parseSettingResponse(String response) throws JsonProcessingException {
        ArrayList<Setting> settings = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonSettings = mapper.readTree(response);
        //the full list of json settings
        JsonNode jsonSetting = jsonSettings.get("settings");

        //iterates through each setting in the settings
        if (jsonSetting != null) {
            for (JsonNode setting : jsonSetting) {

                //iterate through each component required for the setting
                JsonNode requiresJson = setting.get("requires");
                ArrayList<String> requires = new ArrayList<>();
                if (requiresJson != null) {
                    for (JsonNode require : requiresJson) {
                        if (require!= null) requires.add(require.asText());
                    }
                }

                //get id of setting
                String id = "";
                if (setting.get("id") != null) id = setting.get("id").asText();

                //creates the new setting and adds it to the list
                settings.add(new Setting(id, requires));
            }
        }

        return settings;
    }

    /**
     * Transforms a Json string that describes many jukeboxes into a List of Jukebox objects
     *
     * @param response a String with Json formatting that describes Jukebox objects
     * @return a List of Jukebox objects
     * @throws JsonProcessingException
     */
    public static List<Jukebox> parseJukeboxResponse(String response) throws JsonProcessingException {
        ArrayList<Jukebox> jukeboxes = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        //the full json list of objects
        JsonNode jsonJukebox = mapper.readTree(response);
        //iterate through each jukebox in the list
        for (JsonNode jukebox : jsonJukebox){

            //iterate through each component of the jukebox and add it to a list of components
            JsonNode componentJson = jukebox.get("components");
            ArrayList<String> components = new ArrayList<>();
            if (componentJson!=null) {
                for (JsonNode component : componentJson) {
                    if (component.get("name") != null) components.add(component.get("name").asText());
                }
            }

            //get id and name of the jukebox
            String id = "";
            String name = "";
            if (jukebox.get("id")!=null)
                id = jukebox.get("id").asText();
            if (jukebox.get("model")!=null)
                name = jukebox.get("model").asText();

            //creates the new jukebox and adds it to the list to be returned
            jukeboxes.add(new Jukebox(id, name, components));
        }
        return jukeboxes;
    }


    /**
     * a method that sends a GET request to a given url and returns the content of the request
     *
     * @param url the url that is receiving the get request
     * @return a string with the entirety of the contents returned from the get request
     * @throws IOException
     */
    public static String getRequest(URL url) throws IOException {
        URLConnection yc = url.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        yc.getInputStream()));
        String inputLine;
        StringBuilder output = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            output.append(inputLine);
        in.close();
        return output.toString();
    }

}
