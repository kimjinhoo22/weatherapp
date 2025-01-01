package com.acon.weatherapp.dto;

import com.acon.weatherapp.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserInfoDto {
    @Getter
    @Builder
    public static class Response {
        private final String userId;
        private final String name;
        private final LocalDate birth;
        private final String gender;
        private final String address;
        private final String phone;
        private final LocalDateTime createdAt;

        public static Response from(User user) {
            return Response.builder()
                    .userId(user.getUserId())
                    .name(user.getName())
                    .birth(user.getBirth())
                    .gender(user.getGender())
                    .address(user.getAddress())
                    .phone(user.getPhone())
                    .createdAt(user.getCreatedAt())
                    .build();
        }

    }
}
