package org.freeddyns.systempolska.user;

import org.freeddyns.systempolska.user.Model.dto.ReadUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping()
    ResponseEntity<List<ReadUserDto>> readUserSorted(@RequestParam(required = false) String searchKeyword,
                                                     @RequestParam(required = false) String searchBy,
                                                     @RequestParam(required = false) String sortBy,
                                                     @RequestParam(defaultValue = "0") int page) {

            return ResponseEntity.ok(service.connectionToController(searchKeyword,searchBy,sortBy,page));
        }





    @GetMapping("/columns")
    Set<String> names() {
        return service.columnNames();
    }


}

