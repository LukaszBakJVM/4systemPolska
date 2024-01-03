package org.freeddyns.systempolska.Model;

import org.freeddyns.systempolska.Model.Dto.ReadUserDto;
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
                                                     @RequestParam(required = false) String sortBy,
                                                     @RequestParam(defaultValue = "0") int page) {
        if (searchKeyword == null) {
            return ResponseEntity.ok(service.getUsersWithPaginationAndSorting(sortBy, page));
        }
        return ResponseEntity.notFound().build();

        // ResponseEntity.ok(service.getUsersWithPaginationAndSortingAndSearch(searchKeyword,sortBy,page));
    }

    @GetMapping("/colums")
    Set<String> names() {
        return service.columnNames();
    }

}

