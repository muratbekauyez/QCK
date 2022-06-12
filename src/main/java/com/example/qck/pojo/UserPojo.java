package com.example.qck.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Data
public class UserPojo {
    private Long userId;

    private String username;

    private String password;

    private String roles;

    private List<SchoolPojo> schools;

    private List<EduGroupPojo> eduGroups;

    private Integer subject;

    @Override
    public String toString() {
        return "UserPojo{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles='" + roles + '\'' +
                ", schools=" + schools +
                ", eduGroups=" + eduGroups +
                ", subject=" + subject +
                '}';
    }
}
