package com.example.yellowaution.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("board")
public class BoardController {

    @GetMapping("/{page}")
    public String page(@PathVariable String page) {
        return "board/" + page;
    }

    @GetMapping("/{page}/{id}")
    public String pageWithId(@PathVariable String page, @PathVariable String id) {
        return "board/" + page;
    }
}
