package com.example.qck.repository;

import com.example.qck.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
//    Subject findById(Long subjectId);
}
