package com.example.qck.service;

import com.example.qck.config.MyUserDetails;
import com.example.qck.model.*;
import com.example.qck.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TestService {
    
    private final TestRepository testRepository;
    private final TestQuestionRepository testQuestionRepository;
    private final QuestionOptionRepository optionRepository;
    private final UserRepository userRepository;
    private final StudentTestAttemptRepository studentTestAttemptRepository;
    private final StudentQuestionAttemptRepository studentQuestionAttemptRepository;
    private final TeacherCheckRepository teacherCheckRepository;

    public List<Test> getAllTests(){
        return testRepository.findAll();
    }

    public List<Test> getAllEnabledTest(){
        return testRepository.findTestsByEnabled(true);
    }

    public List<Test> getAllEnabledTestsBySubject(User user){
        List<Test> testsToReturn = new ArrayList<>();
        List<Test> allUserAvailableTests = testRepository.findTestsByEnabledAndStudyYear(true, user.getStudyYears());
        for(Test t : allUserAvailableTests){
            Optional<StudentTestAttempt> attemptOptional = studentTestAttemptRepository.findStudentTestAttemptByTestIdAndUserId(t.getId(), user.getId());
            if(attemptOptional.isEmpty()){
                testsToReturn.add(t);
            }
        }

        return testsToReturn;
    }

    public void saveTest(Test test){
        test.setEnabled(true);
        test.setDate(new Date());
        testRepository.save(test);
    }

    public Test getTestById(Long id){
        Optional<Test> test = testRepository.findById(id);
        if(test.isEmpty()) throw new RuntimeException("Test not found for id: " + id);
        return test.get();
    }

//    public List<Test> getTestBySubjectId(Long subjectId){
//        List<Optional<Test>> tests = testRepository.findBy();
//    }

    @Transactional
    public Test addTestQuestion(Long testId, TestQuestion testQuestion){
        testQuestion = setNullQuestionOptionIfEmpty(testQuestion);
        testQuestionRepository.save(testQuestion);
        Test test = getTestById(testId);
        List<TestQuestion> questions = test.getQuestionList();
        questions.add(testQuestion);
        return testRepository.saveAndFlush(test);
    }

    @Transactional
    public void updateTestQuestion(TestQuestion testQuestion, Long questionId){
        testQuestion.setId(questionId);
        Optional<QuestionOption> option = Optional.ofNullable(getTestQuestionById(questionId).getQuestionOption());
        if(option.isPresent()){
            Long optionId = option.get().getId();
            optionRepository.deleteById(optionId);
        }
        testQuestion = setNullQuestionOptionIfEmpty(testQuestion);
        testQuestionRepository.save(testQuestion);

    }

    public void updateTest(Test test){
        Test old = getTestById(test.getId());
        old.setName(test.getName());
        testRepository.save(old);
    }

    public void disableTest(Long id){
        Test test = getTestById(id);
        test.setEnabled(false);
        testRepository.save(test);
    }

    public TestQuestion getTestQuestionById(Long id){
        Optional<TestQuestion> testQuestion = testQuestionRepository.findById(id);
        if(testQuestion.isEmpty()) throw new RuntimeException("Test Question not found for id: " + id);
        return testQuestion.get();
    }


    public void deleteByQuestionId(Long id){
        Optional<QuestionOption> option = Optional.ofNullable(getTestQuestionById(id).getQuestionOption());
        if(option.isPresent()){
            Long questionOptionId = option.get().getId();
            optionRepository.deleteById(questionOptionId);
        }
        testQuestionRepository.deleteTestQuestionsByIdFromTestTestQuestions(id);
        testQuestionRepository.deleteById(id);

    }

    public TestQuestion setNullQuestionOptionIfEmpty(TestQuestion question){
        QuestionType type = question.getType();
        if(type.equals(QuestionType.OPEN)){
            question.setQuestionOption(null);
        }
        return question;
    }

    public void saveStudentTestAttempt(Test temp, MyUserDetails user){
        StudentTestAttempt studentTestAttempt = new StudentTestAttempt();
        Test test = getTestById(temp.getId());
        test.setQuestionList(temp.getQuestionList());


        studentTestAttempt.setUser(userRepository.getUserByUsername(user.getUsername()));
        studentTestAttempt.setTest(test);
        studentTestAttempt.setDatePassed(new Date());
        studentTestAttempt.setStudentQuestionAttempts(new ArrayList<>());

        for(TestQuestion question : test.getQuestionList()){
            StudentQuestionAttempt sqa = new StudentQuestionAttempt();
            sqa.setQuestion(question);
            sqa.setStudentAnswer(question.getStudentAnswer());
            sqa.setTest(test);
            studentTestAttempt.getStudentQuestionAttempts().add(sqa);
        }
        checkAllQuestions(studentTestAttempt);
        studentTestAttemptRepository.save(studentTestAttempt);

    }


    public void checkAllQuestions(StudentTestAttempt testAttempt){
        for(StudentQuestionAttempt questionAttempt : testAttempt.getStudentQuestionAttempts()){
            QuestionType type = questionAttempt.getQuestion().getType();
            String correctAnswer = getTestQuestionById(questionAttempt.getQuestion().getId()).getCorrectAnswer();
            String studentAnswer = questionAttempt.getStudentAnswer();
            if(studentAnswer == null){
                questionAttempt.setIsAnswerCorrect(false);
                continue;
            }
            if(type.equals(QuestionType.MULTIPLE_CHOICE)){
                questionAttempt.setIsAnswerCorrect(studentAnswer.equals(correctAnswer));
            }else {
                questionAttempt.setIsAnswerCorrect(null);
            }
        }
    }

    public List<StudentTestAttempt> allStudentTestAttemptsByTest(Long testId){
        return studentTestAttemptRepository.findAllByTestId(testId);
    }

    public List<TeacherCheck> allTestAttemptResults(Long testId){
        List<TeacherCheck> results = new ArrayList<>();
        List<StudentTestAttempt> studentTestAttempts = studentTestAttemptRepository.findAllByTestId(testId);
        for(StudentTestAttempt testAttempt : studentTestAttempts){
            TeacherCheck teacherCheck = teacherCheckRepository.getTeacherCheckByStudentTestAttemptId(testAttempt.getId());
            if(teacherCheck != null) results.add(teacherCheck);
        }

        return results;
    }

    public StudentTestAttempt getStudentTestAttempt(Long attemptId){
        Optional<StudentTestAttempt> testAttempt = studentTestAttemptRepository.findById(attemptId);
        if(testAttempt.isEmpty())throw new RuntimeException("Test Attempt not found for id: " + attemptId);
        return testAttempt.get();
    }

    public StudentQuestionAttempt getStudentQuestionAttempt(Long attemptId){
        Optional<StudentQuestionAttempt> questionAttempt = studentQuestionAttemptRepository.findById(attemptId);
        if(questionAttempt.isEmpty()) throw new RuntimeException("Question Attempt not found for id: " + attemptId);
        return questionAttempt.get();
    }



}
