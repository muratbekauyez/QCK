package com.example.qck.controller;

import com.example.qck.config.MyUserDetails;
import com.example.qck.model.HelperBlog;
import com.example.qck.model.Role;
import com.example.qck.model.User;
import com.example.qck.repository.UserRepository;
import com.example.qck.service.HelperBlogService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/helperBlogs")
public class HelperBlogController {

    private final UserRepository userRepository;
    private final HelperBlogService helperBlogService;

    @GetMapping
    public String helperBlogPage(Model model,
                                 @AuthenticationPrincipal MyUserDetails userDetails){
        User user = userRepository.getUserByUsername(userDetails.getUsername());
        for (Role role : user.getRoles()){
            if(role.getId().equals(1L)){
                model.addAttribute("listHelperBlogs", helperBlogService.getAllHelperBlogs());
            }else {
                model.addAttribute("listTests", helperBlogService.getAllByUser(user.getId()));
            }
            break;
        }

        model.addAttribute("helperBlog", new HelperBlog());
        return "helper_blogs/helper_blogs";
    }




}
