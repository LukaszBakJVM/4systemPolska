package org.freeddyns.systempolska.Model;


import org.freeddyns.systempolska.Model.Dto.WriteUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

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
    public ResponseEntity<Map<String, Object>> uploadUsers(@RequestParam("file") MultipartFile file) {
        Flux<WriteUserDto> result = service.writeUserToDatabase(file);
        long numberOfEntries = result.collectList().block().size();

        Map<String, Object> response = new HashMap<>();
        response.put("message", numberOfEntries + " entries added.");

        return ResponseEntity.ok(response);
    }
}


