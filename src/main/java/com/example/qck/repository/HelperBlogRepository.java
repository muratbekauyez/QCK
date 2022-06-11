package com.example.qck.repository;

import com.example.qck.model.HelperBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface HelperBlogRepository extends JpaRepository<HelperBlog, Long> {

    @Query("SELECT DISTINCT h.threadId FROM HelperBlog h")
    List<Long> getDistinctHelperBlogsByThreadId();

    List<HelperBlog> findHelperBlogsByFromUserIdOrToUserId(Long fromUserId, Long toUserId);

    List<HelperBlog> findAllByThreadId(Long threadId);

    HelperBlog findFirstByFromUserIdOrToUserId(Long fromUserId, Long toUserId);

    HelperBlog findFirstByThreadId(Long threadId);

    @Query(value = "SELECT max (threadId) FROM HelperBlog ")
    BigDecimal max();
}
