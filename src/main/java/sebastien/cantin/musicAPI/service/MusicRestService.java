package sebastien.cantin.musicAPI.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

@RestController
public class MusicRestService {

    /**
     *
     * @param settingId the Id of the setting that we are checking for compatible jukeboxes of
     * @param model a string that filters jukeboxes by their name (optional)
     * @param offset an integer that defines the index of the returned jukeboxes to start at
     * @param limit an integer that defines how many jukeboxes are to be returned per page
     * @return a paginated list of jukeboxes that satisfy the requirements of the given setting
     */
    @RequestMapping({"/compatibleJukeboxes/{settingId}", "/compatibleJukeboxes/{settingId}/"})
    public static List<Page> getCompatibleJukeboxes(@PathVariable String settingId,
                                                    @RequestParam(required = false) String model,
                                                    @RequestParam(required = false) String offset,
                                                    @RequestParam(required = false) String limit) throws IOException {
        ArrayList<Jukebox> jukeboxes = new ArrayList<>();

        final String finalModel = model;
        int realOffset = 0;
        if (offset!=null) realOffset = Integer.parseInt(offset);
        int realLimit = 10;
        if (limit!=null) realLimit= Integer.parseInt(limit);

        String jukeboxDB = getRequest(new URL("http://my-json-server.typicode.com/touchtunes/tech-assignment/jukes"));
        String settingDB = getRequest(new URL("http://my-json-server.typicode.com/touchtunes/tech-assignment/settings"));

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

        if (correctSetting==null) throw new IllegalArgumentException("The given setting ID was not found in the database");

        //go thru each jukebox to find all the ones that meet requirements
        jukeboxIteration:
        for (Jukebox j : jukeboxList){
            ArrayList<String> componentsCopy = new ArrayList<>(j.getComponents());
            for (String requirement: correctSetting.getRequires()){
                boolean b = componentsCopy.remove(requirement);
                if (!b) continue jukeboxIteration;
            }
            jukeboxes.add(j);
        }


        //filter by name
        if (finalModel!=null){
            for (Jukebox j : jukeboxes){
                if (j.getModel().equals(finalModel)) System.out.println("true");
            }
            jukeboxes.removeIf(jukebox -> !jukebox.getModel().equals(finalModel));
        }

        //paginated list time
        ArrayList<Page> jukeboxes1 = new ArrayList<>();
        Jukebox[] jukeboxes2 = new Jukebox[realLimit];
        for (int i = realOffset; i<jukeboxes.size(); i++){
            if ((i-realOffset)%realLimit==0 && i!=realOffset){
                jukeboxes1.add(new Page((i-realOffset)/realLimit ,jukeboxes2));
                jukeboxes2 = new Jukebox[realLimit];
            }
            jukeboxes2[(i-realOffset)%realLimit] = jukeboxes.get(i);
            if (i==jukeboxes.size()-1) jukeboxes1.add(new Page((i-realOffset)/realLimit, jukeboxes2));
        }


        System.out.println(jukeboxes1);
        return jukeboxes1;
    }

    public static List<Setting> parseSettingResponse(String response) throws JsonProcessingException {
        ArrayList<Setting> settings = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonSettings = mapper.readTree(response);
        JsonNode jsonSetting = jsonSettings.get("settings");

        for (JsonNode setting: jsonSetting){
            JsonNode requiresJson = setting.get("requires");
            ArrayList<String> requires = new ArrayList<>();
            if (requiresJson!=null) {
                for (JsonNode require : requiresJson) {
                    requires.add(require.asText());
                }
            }
            String id="";
            if (setting.get("id")!=null)
                id = setting.get("id").asText();
            settings.add(new Setting(id, requires));
        }

        return settings;
    }

    public static List<Jukebox> parseJukeboxResponse(String response) throws JsonProcessingException {
        ArrayList<Jukebox> jukeboxes = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonJukebox = mapper.readTree(response);
        for (JsonNode jukebox : jsonJukebox){
            JsonNode componentJson = jukebox.get("components");
            ArrayList<String> components = new ArrayList<>();
            if (componentJson!=null) {
                for (JsonNode component : componentJson) {
                    components.add(component.get("name").asText());
                }
            }
            String id = "";
            String name = "";
            if (jukebox.get("id")!=null)
                id = jukebox.get("id").asText();
            if (jukebox.get("model")!=null)
                name = jukebox.get("model").asText();
            Jukebox j = new Jukebox(id, name, components);
            jukeboxes.add(j);
        }
        return jukeboxes;
    }


    /**
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
        String output = "";
        while ((inputLine = in.readLine()) != null)
            output += (inputLine);
        in.close();
        return output;
    }

}
