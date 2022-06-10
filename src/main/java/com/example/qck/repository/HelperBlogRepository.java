package com.example.qck.repository;

import com.example.qck.model.HelperBlog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HelperBlogRepository extends JpaRepository<HelperBlog, Long> {

    List<HelperBlog> findHelperBlogByFromUserIdOrToUserId(Long fromUserId, Long toUserId);

    HelperBlog findFirstByThreadId(Long threadId);
}
