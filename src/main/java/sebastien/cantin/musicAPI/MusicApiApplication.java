package sebastien.cantin.musicAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class MusicApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicApiApplication.class, args);
	}

	@RequestMapping(value=("/"))
	public static String greeting(){
		return "Welcome to my Backend Developer tech assignment!\n\n" +
				"use the url /compatibleJukeboxes/{settingId} with the optional parameters model, limit and offset to receive a paginated list of jukeboxes that support a given setting id";
	}
}
