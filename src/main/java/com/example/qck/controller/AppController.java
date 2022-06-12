package com.example.qck.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/")
    public String indexPage(){
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/error")
    public String errorPage(){
        return "error";
    }
}
