package com.example.qck.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "schools")
@Getter
@Setter
public class School {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "region")
    private String region;
}
