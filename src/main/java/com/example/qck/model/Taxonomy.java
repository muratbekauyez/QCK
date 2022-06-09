package com.example.qck.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "taxonomy")
@Getter
@Setter
public class Taxonomy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TaxonomyModel model;

    @Column(name = "level")
    private Integer level;

    @Column(name = "level_name")
    private String levelName;
}
