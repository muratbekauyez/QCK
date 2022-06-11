package com.example.qck.service;

import com.example.qck.model.HelperBlog;
import com.example.qck.model.User;
import com.example.qck.repository.HelperBlogRepository;
import com.example.qck.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HelperBlogService {

    private final HelperBlogRepository helperBlogRepository;
    private final UserRepository userRepository;

    public List<HelperBlog> getDistinctHeaderBlogsByThread(){
        List<HelperBlog> helperBlogList = new ArrayList<>();
        for(Long l : helperBlogRepository.getDistinctHelperBlogsByThreadId()){
            HelperBlog helperBlog = helperBlogRepository.findFirstByThreadId(l);
            helperBlogList.add(helperBlog);
        }

        return helperBlogList;

    }

    public HelperBlog getHelperBlogById(Long id){
        Optional<HelperBlog> helperBlog = helperBlogRepository.findById(id);
        if(helperBlog.isEmpty()) throw new RuntimeException("Helper Blog not found for id: " + id);
        return helperBlog.get();
    }

    public List<HelperBlog> getAllByUser(Long userId){
        return helperBlogRepository.findHelperBlogsByFromUserIdOrToUserId(userId, userId);
    }

    public List<HelperBlog> getAllByThread(Long threadId){
        return helperBlogRepository.findAllByThreadId(threadId);
    }

    public void saveHelperBlog(User user, HelperBlog helperBlog){
        User student = userRepository.getUserByUsername(user.getUsername());
        User admin = userRepository.getUserByUsername("admin");
        HelperBlog temp = helperBlogRepository.findFirstByFromUserIdOrToUserId(student.getId(), student.getId());

        if(temp == null){
            Long newThreadId = helperBlogRepository.max().longValue() + 1L;
            helperBlog.setThreadId(newThreadId);
        }else{
            helperBlog.setThreadId(temp.getThreadId());
        }


        helperBlog.setFromUser(student);
        helperBlog.setToUser(admin);
        helperBlog.setDate(new Date());

        helperBlogRepository.save(helperBlog);
    }

    public void replyHelperBlog(User user, HelperBlog helperBlog){
        List<HelperBlog> threadHelperBlogs = getAllByThread(helperBlog.getThreadId());

        if(!threadHelperBlogs.get(0).getFromUser().getId().equals(1L)){
            helperBlog.setToUser(threadHelperBlogs.get(0).getFromUser());
        }else{
            helperBlog.setToUser(threadHelperBlogs.get(0).getToUser());
        }


        helperBlog.setFromUser(userRepository.getUserByUsername(user.getUsername()));
        helperBlog.setDate(new Date());
        helperBlogRepository.save(helperBlog);
    }


}
