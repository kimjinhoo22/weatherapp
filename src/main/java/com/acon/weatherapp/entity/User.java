package com.acon.weatherapp.entity;

//데이터베이스와 매칭될 도메인
//DTO 와 핵심 도메인 분리
//컨트롤러에는 도메인을 호출하지 않음.

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

}
