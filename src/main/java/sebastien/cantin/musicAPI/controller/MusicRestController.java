package sebastien.cantin.musicAPI.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sebastien.cantin.musicAPI.service.MusicRestService;

@RestController
public class MusicRestController {

    @RequestMapping({"/compatibleJukeboxes/{settingId}", "/compatibleJukeboxes/{settingId}/"})
    public static ResponseEntity<?> getCompatibleJukeboxes(@PathVariable String settingId,
                                                        @RequestParam(required = false) String model,
                                                        @RequestParam(required = false) String offset,
                                                        @RequestParam(required = false) String limit) {
        try {
            return ResponseEntity.ok(MusicRestService.getCompatibleJukeboxes(settingId, model, offset, limit));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
