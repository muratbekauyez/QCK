package com.example.qck.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "student_question_attempts")
@Getter
@Setter
public class StudentQuestionAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "test_id")
    private Test test;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "question_id")
    private TestQuestion question;

    @Column(name = "student_answer")
    private String studentAnswer;

}
