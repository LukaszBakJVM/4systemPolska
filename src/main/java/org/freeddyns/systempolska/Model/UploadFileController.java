package org.freeddyns.systempolska.Model;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import reactor.core.publisher.Mono;

import java.util.HashMap;

import java.util.Map;


@RestController
@RequestMapping()
public class UploadFileController {
    private final UserService service;

    public UploadFileController(UserService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public Mono<ResponseEntity<Map<String, Object>>> uploadUsers(@RequestParam("file") MultipartFile file) {
        return   service.writeUserToDatabase(file).map(writeUserDto -> { long numberOfEntries =writeUserDto.size();



                Map<String, Object> response = new HashMap<>();
            response.put("message", numberOfEntries + " entries added.");
            return ResponseEntity.ok(response);
        });
    }
}




