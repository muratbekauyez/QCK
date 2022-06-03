package com.example.qck.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "student_test_attempts")
@Getter
@Setter
public class StudentTestAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "test_id")
    private Test test;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "student_test_question_attempts",
            joinColumns = {@JoinColumn(name = "test_attempt_id")},
            inverseJoinColumns = {@JoinColumn(name = "question_attempt_id")}
    )
    private List<StudentQuestionAttempt> studentQuestionAttempts;

    @Column(name = "result")
    private Integer result;

    @Column(name = "date_passed")
    private Date datePassed;
}
