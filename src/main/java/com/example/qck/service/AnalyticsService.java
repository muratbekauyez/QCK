package com.example.qck.service;
import com.example.qck.model.StudentTestAttempt;
import com.example.qck.repository.TeacherCheckRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnalyticsService {
    private final TeacherCheckRepository teacherCheckRepository;
    private final StudentTestAttempt studentTestAttempt;



}
