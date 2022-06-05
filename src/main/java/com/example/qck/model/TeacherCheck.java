package com.example.qck.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "techer_check")
@Getter
@Setter
public class TeacherCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @OneToOne
    @JoinColumn(name = "student_test_attempt_id", referencedColumnName = "id")
    private StudentTestAttempt studentTestAttempt;

    @Column(name = "result")
    private Integer result;

}
