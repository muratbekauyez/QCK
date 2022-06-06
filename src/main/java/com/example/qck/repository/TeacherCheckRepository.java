package com.example.qck.repository;

import com.example.qck.model.TeacherCheck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherCheckRepository extends JpaRepository<TeacherCheck, Long> {
    TeacherCheck getTeacherCheckByStudentTestAttemptIdAndTeacherId(Long studentTestAttemptId, Long teacherId);
}
