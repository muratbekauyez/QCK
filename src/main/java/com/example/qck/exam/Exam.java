package com.example.qck.exam;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "exams")
@Getter
@Setter
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "upload_time")
    private Date uploadTime;

    @Column(name = "filename")
    private String filename;

    @Column(name = "content")
    private byte[] content;
}
