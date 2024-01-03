package org.freeddyns.systempolska.Model;



import org.freeddyns.systempolska.Model.Dto.ReadUserDto;
import org.freeddyns.systempolska.Model.Dto.WriteUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UploadFileController {
    private final UserService service;

    public UploadFileController(UserService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadUsers(@RequestParam("file") MultipartFile file) {
        Flux<WriteUserDto> result = service.saveUsersFromFile(file);
        long numberOfEntries = result.collectList().block().size();

        Map<String, Object> response = new HashMap<>();
        response.put("message", numberOfEntries + " entries added.");

        return ResponseEntity.ok(response);
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
