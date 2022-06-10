package com.example.qck.service;

import com.example.qck.model.HelperBlog;
import com.example.qck.model.User;
import com.example.qck.repository.HelperBlogRepository;
import com.example.qck.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HelperBlogService {

    private final HelperBlogRepository helperBlogRepository;
    private final UserRepository userRepository;

    public List<HelperBlog> getAllHelperBlogs(){
        return helperBlogRepository.findAll();
    }

    public List<HelperBlog> getAllByUser(Long userId){
        return helperBlogRepository.findHelperBlogByFromUserIdOrToUserId(userId, userId);
    }

    public void saveHelperBlog(User user, HelperBlog helperBlog){
        User student = userRepository.getUserByUsername(user.getUsername());
        User admin = userRepository.getUserByUsername("admin");
        if(!user.getId().equals(1L)){
            helperBlog.setFromUser(student);
            helperBlog.setToUser(admin);
        }else{
            HelperBlog temp = helperBlogRepository.findFirstByThreadId(helperBlog.getThreadId());
            helperBlog.setFromUser(admin);
            if(temp.getFromUser().getId().equals(1L)) helperBlog.setToUser(temp.getToUser());
            else helperBlog.setToUser(temp.getFromUser());
        }

        helperBlogRepository.save(helperBlog);
    }


}
