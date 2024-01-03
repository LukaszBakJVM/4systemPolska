package org.freeddyns.systempolska.Model;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping(value = "/upload",  consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Flux<WriteUserDto> uploadUsers(@RequestParam("file") MultipartFile file) {
        return service.saveUsersFromFile(file);
    }

    @GetMapping()
    ResponseEntity<List<ReadUserDto>> readUserSorted(@RequestParam(required = false) String searchKeyword,
                                                     @RequestParam(required = false) String sortBy,
                                                     @RequestParam(defaultValue = "0") int page){
        if (searchKeyword == null){
            return ResponseEntity.ok(service.findAll(page));
        }
        return ResponseEntity.ok(service.getUsersWithPaginationAndSortingAndSearch(searchKeyword,sortBy,page));
    }

}
