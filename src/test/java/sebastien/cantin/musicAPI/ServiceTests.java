package sebastien.cantin.musicAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import sebastien.cantin.musicAPI.model.Jukebox;
import sebastien.cantin.musicAPI.model.Page;
import sebastien.cantin.musicAPI.service.MusicRestService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ServiceTests {

    @Test
    public void testSuccessfulService() {
        String settingId = "aae445bf-72f0-4680-a23e-18fcf7241f8b";
        String model=null;
        String limit=null;
        String offset=null;
        String error;
        List<Page> response = new ArrayList<>();
        try {
            response = MusicRestService.getCompatibleJukeboxes(settingId, model, offset, limit);
        }
            catch (Exception e){
            error = e.getMessage();
        }
        assertThat(response.size()).isEqualTo(30);
    }

    @Test
    public void testSuccessfulServiceWithEmptyListResponse() {
        String settingId = "2f58dbd4-47cb-4eef-bb72-623f4aa4fe5d";
        String model=null;
        String limit=null;
        String offset=null;
        String error;
        List<Page> response = new ArrayList<>();
        try {
            response = MusicRestService.getCompatibleJukeboxes(settingId, model, offset, limit);
        }
        catch (Exception e){
            error = e.getMessage();
        }
        assertThat(response.size()).isEqualTo(0);
    }

    @Test
    public void testSuccessfulLimit() {
        String settingId = "aae445bf-72f0-4680-a23e-18fcf7241f8b";
        String model=null;
        String limit="15";
        String offset=null;
        String error;
        List<Page> response = new ArrayList<>();
        try {
            response = MusicRestService.getCompatibleJukeboxes(settingId, model, offset, limit);
        }
        catch (Exception e){
            error = e.getMessage();
        }
        assertThat(response.size()).isEqualTo(15);
    }

    @Test
    public void testSuccessfulOffset() {
        String settingId = "aae445bf-72f0-4680-a23e-18fcf7241f8b";
        String model=null;
        String limit=null;
        String offset="15";
        String error;
        List<Page> response = new ArrayList<>();
        try {
            response = MusicRestService.getCompatibleJukeboxes(settingId, model, offset, limit);
        }
        catch (Exception e){
            error = e.getMessage();
        }
        assertThat(response.size()).isEqualTo(15);
    }

    @Test
    public void testSuccessfulModel() {
        String settingId = "aae445bf-72f0-4680-a23e-18fcf7241f8b";
        String model="angelina";
        String limit=null;
        String offset=null;
        String error;
        List<Page> response = new ArrayList<>();
        try {
            response = MusicRestService.getCompatibleJukeboxes(settingId, model, offset, limit);
        }
        catch (Exception e){
            error = e.getMessage();
        }
        for (Page p : response){
            assertThat(p.getJukebox().getModel()).isEqualTo(model);
        }
    }

    @Test
    public void testSuccessfulLimitAndOffset() {
        String settingId = "aae445bf-72f0-4680-a23e-18fcf7241f8b";
        String model=null;
        String limit="5";
        String offset="15";
        String error;
        List<Page> response = new ArrayList<>();
        try {
            response = MusicRestService.getCompatibleJukeboxes(settingId, model, offset, limit);
        }
        catch (Exception e){
            error = e.getMessage();
        }
        assertThat(response.size()).isEqualTo(5);
        assertThat(response.get(0).getJukebox().getId()).isEqualTo("5ca94a8afa2bc9887b28ce87");
    }

    @Test
    public void testSuccessfulLimitAndModel() {
        String settingId = "aae445bf-72f0-4680-a23e-18fcf7241f8b";
        String model="angelina";
        String limit="5";
        String offset=null;
        String error;
        List<Page> response = new ArrayList<>();
        try {
            response = MusicRestService.getCompatibleJukeboxes(settingId, model, offset, limit);
        }
        catch (Exception e){
            error = e.getMessage();
        }
        assertThat(response.size()).isEqualTo(5);
        for (Page p : response){
            assertThat(p.getJukebox().getModel()).isEqualTo(model);
        }
    }

    @Test
    public void testSuccessfulModelAndOffset() {
        String settingId = "aae445bf-72f0-4680-a23e-18fcf7241f8b";
        String model="angelina";
        String limit=null;
        String offset="3";
        String error;
        List<Page> response = new ArrayList<>();
        try {
            response = MusicRestService.getCompatibleJukeboxes(settingId, model, offset, limit);
        }
        catch (Exception e){
            error = e.getMessage();
        }
        assertThat(response.size()).isEqualTo(7);
        for (Page p : response){
            assertThat(p.getJukebox().getModel()).isEqualTo(model);
        }
    }

    @Test
    public void testSuccessfulModelAndOffsetAndLimit() {
        String settingId = "aae445bf-72f0-4680-a23e-18fcf7241f8b";
        String model="angelina";
        String limit="5";
        String offset="3";
        String error;
        List<Page> response = new ArrayList<>();
        try {
            response = MusicRestService.getCompatibleJukeboxes(settingId, model, offset, limit);
        }
        catch (Exception e){
            error = e.getMessage();
        }
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
        String limit=null;
        String offset=null;
        String error;
        List<Page> response = new ArrayList<>();
        try {
            response = MusicRestService.getCompatibleJukeboxes(settingId, model, offset, limit);
        }
        catch (Exception e){
            error = e.getMessage();
        }
        assertThat(response.size()).isEqualTo(30);
    }

    @Test
    public void testUnsuccessfulSettingId() {
        String settingId = "aae445bf-72f0-4680-a23e-18fcf7241f8";
        String model=null;
        String limit=null;
        String offset=null;
        String error = "";
        List<Page> response = new ArrayList<>();
        try {
            response = MusicRestService.getCompatibleJukeboxes(settingId, model, offset, limit);
        }
        catch (Exception e){
            error = e.getMessage();
        }

        assertThat(error).isEqualTo("Error: "+ HttpStatus.NOT_FOUND+". The given setting ID was not found in the database");
    }

    @Test
    public void testUnsuccessfulModel() {
        String settingId = "aae445bf-72f0-4680-a23e-18fcf7241f8b";
        String model="hi";
        String limit=null;
        String offset=null;
        String error = " ";
        List<Page> response = new ArrayList<>();
        try {
            response = MusicRestService.getCompatibleJukeboxes(settingId, model, offset, limit);
        }
        catch (Exception e){
            error = e.getMessage();
        }

        assertThat(error).isEqualTo("Error: "+ HttpStatus.NOT_ACCEPTABLE+". There are no jukeboxes in the database with the input model name");
    }

    @Test
    public void testUnsuccessfulLimit() {
        String settingId = "aae445bf-72f0-4680-a23e-18fcf7241f8b";
        String model=null;
        String limit="-1";
        String offset=null;
        String error = "";
        List<Page> response = new ArrayList<>();
        try {
            response = MusicRestService.getCompatibleJukeboxes(settingId, model, offset, limit);
        }
        catch (Exception e){
            error = e.getMessage();
        }

        assertThat(error).isEqualTo("Error: "+ HttpStatus.NOT_ACCEPTABLE+". The input limit is smaller than 1");
    }

    @Test
    public void testUnsuccessfulOffset() {
        String settingId = "aae445bf-72f0-4680-a23e-18fcf7241f8b";
        String model=null;
        String limit=null;
        String offset="-1";
        String error = "";
        List<Page> response = new ArrayList<>();
        try {
            response = MusicRestService.getCompatibleJukeboxes(settingId, model, offset, limit);
        }
        catch (Exception e){
            error = e.getMessage();
        }

        assertThat(error).isEqualTo("Error: "+ HttpStatus.NOT_ACCEPTABLE+". The input offset is smaller than 0");
    }
}
