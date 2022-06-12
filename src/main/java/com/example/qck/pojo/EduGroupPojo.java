package com.example.qck.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EduGroupPojo {
    private Long id;
    private String name;
    private Integer studyyear;
    private List<SubjectPojo> subjects;

    @Override
    public String toString() {
        return "EduGroupPojo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", studyyear=" + studyyear +
                ", subjects=" + subjects +
                '}';
    }
}
