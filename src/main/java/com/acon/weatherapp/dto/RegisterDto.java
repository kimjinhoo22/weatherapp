package com.acon.weatherapp.dto;

import com.acon.weatherapp.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class RegisterDto {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{
        @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]{5,20}$", message = "아이디는 5~20자의 영문, 숫자로 구성되어야 합니다.")
        private String userId;
        @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+])[A-Za-z\\d!@#$%^&*()_+]{8,20}$",
                message = "비밀번호는 8자 이상, 20자 이하로 소문자, 대문자, 숫자, 특수문자가 포함되어야 합니다." )
        private String password;
        @NotBlank(message = "비밀번호 확인을 입력해주세요")
        private String confirmPassword;
        @NotBlank(message = "이름을 입력하세요")
        private String name;
        @NotNull(message = "생년월일을 입력하세요")
        private LocalDate birth;
        @NotNull(message = "성별을 입력하세요")
        private String gender;
        @NotBlank(message = "주소를 입력하세요")
        private String address;
        @NotBlank(message = "핸드폰 번호를 입력하세요")
        private String phone;

        public Request(String userId){
            this.userId = userId;
        }

        // RequestDTO -> User
        public User toEntity() {
            User user = User.builder()
                    .userId(userId)
                    .name(name)
                    .birth(birth)
                    .password(password)
                    .gender(gender)
                    .address(address)
                    .phone(phone)
                    .role("USER")
                    .build();

            return  user;
        }
    }
    @Getter
    @Builder
    public static class Response {
        private final String userId;
        private final String name;
        private final LocalDateTime createdAt;

        public static Response from(User user) {
            return Response.builder()
                    .userId(user.getUserId())
                    .name(user.getName())
                    .createdAt(user.getCreatedAt())
                    .build();
        }
    }
}
