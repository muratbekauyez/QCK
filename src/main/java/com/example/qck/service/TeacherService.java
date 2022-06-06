package com.example.qck.service;

import com.example.qck.config.MyUserDetails;
import com.example.qck.model.StudentQuestionAttempt;
import com.example.qck.model.StudentTestAttempt;
import com.example.qck.model.TeacherCheck;
import com.example.qck.repository.TeacherCheckRepository;
import com.example.qck.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TeacherService {
    
    private final TestService testService;
    private final UserRepository userRepository;
    private final TeacherCheckRepository teacherCheckRepository;

    public void saveChecking(StudentTestAttempt testAttempt, MyUserDetails user){
        List<StudentQuestionAttempt> enteredQuestions = testAttempt.getStudentQuestionAttempts();
        List<StudentQuestionAttempt> questionAttemptsByTestAttempt = new ArrayList<>();
        StudentTestAttempt studentTestAttempt = testService.getStudentTestAttempt(testAttempt.getId());
        Integer result = 0;
        TeacherCheck teacherCheck = new TeacherCheck();

        for(StudentQuestionAttempt entity : enteredQuestions){
            StudentQuestionAttempt questionAttempt = testService.getStudentQuestionAttempt(entity.getId());
            if(questionAttempt.getIsAnswerCorrect() == null) {
                questionAttempt.setIsAnswerCorrect(entity.getIsAnswerCorrect());
            }
            if(questionAttempt.getIsAnswerCorrect()) result++;
            questionAttemptsByTestAttempt.add(questionAttempt);
        }
        testAttempt.setStudentQuestionAttempts(questionAttemptsByTestAttempt);

        teacherCheck.setStudentTestAttempt(studentTestAttempt);
        teacherCheck.setResult(result);
        teacherCheck.setTeacher(userRepository.getUserByUsername(user.getUsername()));
        teacherCheckRepository.save(teacherCheck);
    }
}
