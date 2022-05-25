package com.example.qck.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/")
    public String indexPage(){
        return "index";
    }

    @GetMapping("/error")
    public String errorPage(){
        return "error";
    }
}
