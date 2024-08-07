package org.freeddyns.systempolska.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    String index() {
        return "index.html";
    }

    @GetMapping("/upload")
    String upload() {
        return "upload.html";
    }

    @GetMapping("/search")
    String search() {
        return "search.html";
    }


}
