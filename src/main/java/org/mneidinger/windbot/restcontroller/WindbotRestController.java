package org.mneidinger.windbot.restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/windbot")
public class WindbotRestController {

    @DeleteMapping("commands")
    public ResponseEntity<String> deleteCommands(){

        return ResponseEntity.ok().body("Ok!");
    }
}
