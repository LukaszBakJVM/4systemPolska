package org.freeddyns.systempolska.Model;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UploadFileController {
    private final UserService service;

    public UploadFileController(UserService service) {
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
            return ResponseEntity.ok(service.getUsersWithPaginationAndSorting( sortBy,page));
        }
        return ResponseEntity.notFound().build();

               // ResponseEntity.ok(service.getUsersWithPaginationAndSortingAndSearch(searchKeyword,sortBy,page));
    }
    @GetMapping("/colums")
    Set<String> names(){
        return service.columnNames();
    }

}
