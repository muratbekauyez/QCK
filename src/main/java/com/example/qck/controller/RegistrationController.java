package com.example.qck.controller;

import com.example.qck.model.User;
import com.example.qck.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Controller
@RequestMapping("/signup")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @GetMapping
    public String testsPage(Model model){
        model.addAttribute("user", new User());
        return "register";
    }


    @PostMapping("/save")
    public String registration(@ModelAttribute("user")User user) throws IOException {
        registrationService.register(user);
        return "redirect:/login";
    }
}
