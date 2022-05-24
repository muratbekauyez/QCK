package com.example.qck.exam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository  extends JpaRepository<Exam, Long> {
}
