package com.example.qck.repository;

import com.example.qck.model.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findTestsByEnabled(Boolean enabled);
}
