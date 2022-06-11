package com.example.qck.repository;

import com.example.qck.model.TeacherCheck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherCheckRepository extends JpaRepository<TeacherCheck, Long> {
    TeacherCheck getTeacherCheckByStudentTestAttemptIdAndTeacherId(Long studentTestAttemptId, Long teacherId);

    TeacherCheck getTeacherCheckByStudentTestAttemptId (Long studentTestAttemptId);
}
