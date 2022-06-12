package com.example.qck.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SchoolPojo {
    private Long id;
    private String name;
    private String region;
    private List<Integer> edugroups;

    @Override
    public String toString() {
        return "SchoolPojo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", edugroups=" + edugroups +
                '}';
    }
}
