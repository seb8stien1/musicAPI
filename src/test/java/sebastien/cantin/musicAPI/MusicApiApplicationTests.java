package sebastien.cantin.musicAPI;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import sebastien.cantin.musicAPI.model.Jukebox;
import sebastien.cantin.musicAPI.model.Page;

import java.util.ArrayList;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Sebastien Cantin
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MusicApiApplicationTests {

	@Test
	void contextLoads() {
	}


	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void greetingShouldReturnDefaultMessage(){
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
				String.class)).contains("Welcome to my Backend Developer tech assignment!");
	}

	@Test
	public void testSuccessfulController() {
		String settingId = "aae445bf-72f0-4680-a23e-18fcf7241f8b";
		String model=null;
		Integer limit=null;
		Integer offset=null;
		String url = "http://localhost:" + port + "/compatibleJukeboxes/"+settingId+"?model="+ Objects.toString(model,"")+"&limit="+Objects.toString(limit,"")+"&offset="+Objects.toString(offset,"");
		ArrayList<Page> response = interpretResponse(this.restTemplate.getForObject(url, JsonNode.class));
		assertThat(response.size()).isEqualTo(30);
	}

	@Test
	public void testSuccessfulLimit() {
		String settingId = "aae445bf-72f0-4680-a23e-18fcf7241f8b";
		String model=null;
		Integer limit=15;
		Integer offset=null;
		String url = "http://localhost:" + port + "/compatibleJukeboxes/"+settingId+"?model="+ Objects.toString(model,"")+"&limit="+Objects.toString(limit,"")+"&offset="+Objects.toString(offset,"");
		ArrayList<Page> response = interpretResponse(this.restTemplate.getForObject(url, JsonNode.class));
		assertThat(response.size()).isEqualTo(15);
	}

	@Test
	public void testSuccessfulOffset() {
		String settingId = "aae445bf-72f0-4680-a23e-18fcf7241f8b";
		String model=null;
		Integer limit=null;
		Integer offset=15;
		String url = "http://localhost:" + port + "/compatibleJukeboxes/"+settingId+"?model="+ Objects.toString(model,"")+"&limit="+Objects.toString(limit,"")+"&offset="+Objects.toString(offset,"");
		ArrayList<Page> response = interpretResponse(this.restTemplate.getForObject(url, JsonNode.class));
		assertThat(response.size()).isEqualTo(15);
	}

	@Test
	public void testSuccessfulModel() {
		String settingId = "aae445bf-72f0-4680-a23e-18fcf7241f8b";
		String model="angelina";
		Integer limit=null;
		Integer offset=null;
		String url = "http://localhost:" + port + "/compatibleJukeboxes/"+settingId+"?model="+ Objects.toString(model,"")+"&limit="+Objects.toString(limit,"")+"&offset="+Objects.toString(offset,"");
		ArrayList<Page> response = interpretResponse(this.restTemplate.getForObject(url, JsonNode.class));
		for (Page p : response){
			assertThat(p.getJukebox().getModel()).isEqualTo(model);
		}
	}

	@Test
	public void testSuccessfulLimitAndOffset() {
		String settingId = "aae445bf-72f0-4680-a23e-18fcf7241f8b";
		String model=null;
		Integer limit=5;
		Integer offset=15;
		String url = "http://localhost:" + port + "/compatibleJukeboxes/"+settingId+"?model="+ Objects.toString(model,"")+"&limit="+Objects.toString(limit,"")+"&offset="+Objects.toString(offset,"");
		ArrayList<Page> response = interpretResponse(this.restTemplate.getForObject(url, JsonNode.class));
		assertThat(response.size()).isEqualTo(5);
		assertThat(response.get(0).getJukebox().getId()).isEqualTo("5ca94a8afa2bc9887b28ce87");
	}

	@Test
	public void testSuccessfulLimitAndModel() {
		String settingId = "aae445bf-72f0-4680-a23e-18fcf7241f8b";
		String model="angelina";
		Integer limit=5;
		Integer offset=null;
		String url = "http://localhost:" + port + "/compatibleJukeboxes/"+settingId+"?model="+ Objects.toString(model,"")+"&limit="+Objects.toString(limit,"")+"&offset="+Objects.toString(offset,"");
		ArrayList<Page> response = interpretResponse(this.restTemplate.getForObject(url, JsonNode.class));
		assertThat(response.size()).isEqualTo(5);
		for (Page p : response){
			assertThat(p.getJukebox().getModel()).isEqualTo(model);
		}
	}

	@Test
	public void testSuccessfulModelAndOffset() {
		String settingId = "aae445bf-72f0-4680-a23e-18fcf7241f8b";
		String model="angelina";
		Integer limit=null;
		Integer offset=3;
		String url = "http://localhost:" + port + "/compatibleJukeboxes/"+settingId+"?model="+ Objects.toString(model,"")+"&limit="+Objects.toString(limit,"")+"&offset="+Objects.toString(offset,"");
		ArrayList<Page> response = interpretResponse(this.restTemplate.getForObject(url, JsonNode.class));
		assertThat(response.size()).isEqualTo(7);
		for (Page p : response){
			assertThat(p.getJukebox().getModel()).isEqualTo(model);
		}
	}

	@Test
	public void testSuccessfulModelAndOffsetAndLimit() {
		String settingId = "aae445bf-72f0-4680-a23e-18fcf7241f8b";
		String model="angelina";
		Integer limit=5;
		Integer offset=3;
		String url = "http://localhost:" + port + "/compatibleJukeboxes/"+settingId+"?model="+ Objects.toString(model,"")+"&limit="+Objects.toString(limit,"")+"&offset="+Objects.toString(offset,"");
		ArrayList<Page> response = interpretResponse(this.restTemplate.getForObject(url, JsonNode.class));
		assertThat(response.size()).isEqualTo(5);
		assertThat(response.get(0).getJukebox().getId()).isEqualTo("5ca94a8a8b58770bb38055a0");
		for (Page p : response){
			assertThat(p.getJukebox().getModel()).isEqualTo(model);
		}
	}

	@Test
	public void testInputOnlySpaces() {
		String settingId = "aae445bf-72f0-4680-a23e-18fcf7241f8b";
		String model="     ";
		Integer limit=null;
		Integer offset=null;
		String url = "http://localhost:" + port + "/compatibleJukeboxes/"+settingId+"?model="+ Objects.toString(model,"")+"&limit="+Objects.toString(limit,"")+"&offset="+Objects.toString(offset,"");
		ArrayList<Page> response = interpretResponse(this.restTemplate.getForObject(url, JsonNode.class));
		assertThat(response.size()).isEqualTo(30);
	}

	@Test
	public void testUnsuccessfulSettingId() {
		String settingId = "aae445bf-72f0-4680-a23e-18fcf7241f8";
		String model=null;
		Integer limit=null;
		Integer offset=null;
		String url = "http://localhost:" + port + "/compatibleJukeboxes/"+settingId+"?model="+ Objects.toString(model,"")+"&limit="+Objects.toString(limit,"")+"&offset="+Objects.toString(offset,"");

		String response = this.restTemplate.getForObject(url, String.class);

		assertThat(response).isEqualTo("Error: "+ HttpStatus.NOT_FOUND+". The given setting ID was not found in the database");
	}

	@Test
	public void testUnsuccessfulModel() {
		String settingId = "aae445bf-72f0-4680-a23e-18fcf7241f8b";
		String model="hi";
		Integer limit=null;
		Integer offset=null;
		String url = "http://localhost:" + port + "/compatibleJukeboxes/"+settingId+"?model="+ Objects.toString(model,"")+"&limit="+Objects.toString(limit,"")+"&offset="+Objects.toString(offset,"");

		String response = this.restTemplate.getForObject(url, String.class);

		assertThat(response).isEqualTo("Error: "+ HttpStatus.NOT_ACCEPTABLE+". There are no jukeboxes in the database with the input model name");
	}

	@Test
	public void testUnsuccessfulLimit() {
		String settingId = "aae445bf-72f0-4680-a23e-18fcf7241f8b";
		String model=null;
		Integer limit=-1;
		Integer offset=null;
		String url = "http://localhost:" + port + "/compatibleJukeboxes/"+settingId+"?model="+ Objects.toString(model,"")+"&limit="+Objects.toString(limit,"")+"&offset="+Objects.toString(offset,"");

		String response = this.restTemplate.getForObject(url, String.class);

		assertThat(response).isEqualTo("Error: "+ HttpStatus.NOT_ACCEPTABLE+". The input limit is smaller than 1");
	}

	@Test
	public void testUnsuccessfulOffset() {
		String settingId = "aae445bf-72f0-4680-a23e-18fcf7241f8b";
		String model=null;
		Integer limit=null;
		Integer offset=-1;
		String url = "http://localhost:" + port + "/compatibleJukeboxes/"+settingId+"?model="+ Objects.toString(model,"")+"&limit="+Objects.toString(limit,"")+"&offset="+Objects.toString(offset,"");

		String response = this.restTemplate.getForObject(url, String.class);

		assertThat(response).isEqualTo("Error: "+ HttpStatus.NOT_ACCEPTABLE+". The input offset is smaller than 0");
	}

	/**
	 * Transforms the Json String returned from the GET request to an ArrayList of Pages
	 * @param j json string in JsonNode form
	 * @return ArrayList of all the pages in the response
	 */
	private static ArrayList<Page> interpretResponse(JsonNode j){
		ArrayList<Page> response = new ArrayList<>();
		for (JsonNode page: j) response.add(transformToPage(page));
		return response;
	}

	/**
	 * Transforms a Json string into a Page object
	 * @param jsonNode json string that corresponds to one page
	 * @return the corresponding Page object
	 */
	private static Page transformToPage(JsonNode jsonNode){
		return new Page(jsonNode.get("number").asInt(), transformToJukebox(jsonNode.get("jukebox")));
	}

	/**
	 * Transforms a Json string into a Jukebox object
	 * @param jsonNode json string that corresponds to one jukebox
	 * @return the corresponding Jukebox object
	 */
	private static Jukebox transformToJukebox(JsonNode jsonNode){
		JsonNode c= jsonNode.get("components");
		ArrayList<String> components = new ArrayList<>();
		for (JsonNode component: c) components.add(component.asText());
		return new Jukebox(jsonNode.get("id").asText(),jsonNode.get("model").asText(),components);
	}

}
