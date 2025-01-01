package com.acon.weatherapp.dto;

import com.acon.weatherapp.entity.User;
import lombok.Builder;
import lombok.Getter;

public class LoginDto {
    @Getter
    @Builder
    public static class Request {
        private String userId;
        private String password;
    }

    @Getter
    @Builder
    public static class Response {
        private final String userId;
        private final String role;

        public static Response from(User user) {
            return Response.builder()
                    .userId(user.getUserId())
                    .role(user.getRole())
                    .build();
        }
    }
}
