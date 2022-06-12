package com.example.qck.repository;

import com.example.qck.model.StudentQuestionAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.lang.annotation.Native;
import java.util.List;

public interface StudentQuestionAttemptRepository extends JpaRepository<StudentQuestionAttempt, Long> {
    List<StudentQuestionAttempt> getStudentQuestionAttemptByTestId(Long testId);

    @Query(value = "select \n" +
            "cast(count(CASE WHEN sqa.is_answer_correct THEN 1 END) as double precision) / cast(count(sqa.is_answer_correct) as double precision)\n" +
            "from student_test_attempts sta \n" +
            "inner join student_test_question_attempts stqa on sta.id=stqa.test_attempt_id\n" +
            "inner join student_question_attempts sqa on stqa.question_attempt_id=sqa.id\n" +
            "inner join test_questions as tq on sqa.question_id=tq.id\n" +
            "inner join learning_objectives as lo on tq.learning_objective_id=lo.id\n" +
            "where lo.id=learning_objective_id\n"+
            "group by user_id", nativeQuery = true)
    List<Object[]> getAnalytics(@Param("learning_objective_id") Long learningObjectiveId);

}
