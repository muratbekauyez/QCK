package com.example.qck.controller;

import com.example.qck.config.MyUserDetails;
import com.example.qck.model.HelperBlog;
import com.example.qck.model.Role;
import com.example.qck.model.User;
import com.example.qck.repository.UserRepository;
import com.example.qck.service.HelperBlogService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                model.addAttribute("distinctThreads", helperBlogService.getDistinctHeaderBlogsByThread());
            }else {
                model.addAttribute("listHelperBlogs", helperBlogService.getAllByUser(user.getId()));
            }
            break;
        }

        model.addAttribute("helperBlog", new HelperBlog());
        return "helper_blogs/helper_blogs";
    }

    @PostMapping("/saveHelperBlog")
    public String saveHelperBlog(@ModelAttribute("helperBlog") HelperBlog helperBlog,
                                 @AuthenticationPrincipal MyUserDetails userDetails){
        User user = userRepository.getUserByUsername(userDetails.getUsername());
        helperBlogService.saveHelperBlog(user,helperBlog);
        return "redirect:/";
    }

    @GetMapping("/thread/{id}")
    public String getAllByThreadId(@PathVariable(name = "id") Long id,
                                   Model model){
        List<HelperBlog> threadHelperBlogs = helperBlogService.getAllByThread(id);
        model.addAttribute("threadId", id);
        model.addAttribute("helperBlog", new HelperBlog());
        model.addAttribute("threadHelperBlogs", threadHelperBlogs);
        return "helper_blogs/thread_helper_blogs";
    }


    @PostMapping("/replyToBlog")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String replyHelperBlog(@ModelAttribute("helperBlog") HelperBlog helperBlog,
                                 @AuthenticationPrincipal MyUserDetails userDetails){

        User user = userRepository.getUserByUsername(userDetails.getUsername());
        helperBlogService.replyHelperBlog(user, helperBlog);
        return "redirect:/";
    }



}
