package com.example.qck.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectPojo {
    private Long id;
    private String name;
    private String knowledgeArea;

    @Override
    public String toString() {
        return "SubjectPojo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", knowledgeArea='" + knowledgeArea + '\'' +
                '}';
    }
}
