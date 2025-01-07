package com.acon.weatherapp.entity;

//데이터베이스와 매칭될 도메인
//DTO 와 핵심 도메인 분리
//컨트롤러에는 도메인을 호출하지 않음.

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class User {
    private Long id;
    private String name;
    private String userId;
    private String password;
    private LocalDate birth;
    private String gender;
    private String address;
    private String phone;
    private LocalDateTime createdAt;
    private String role;

    public User(String name , String userId){
        this.name = name;
        this.userId = userId;
    }

}
