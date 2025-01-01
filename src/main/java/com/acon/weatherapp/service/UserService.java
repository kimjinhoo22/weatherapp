package com.acon.weatherapp.service;

import com.acon.weatherapp.dto.LoginDto;
import com.acon.weatherapp.dto.RegisterDto;
import com.acon.weatherapp.dto.UserInfoDto;
import com.acon.weatherapp.entity.User;
import com.acon.weatherapp.exception.DuplicateException;
import com.acon.weatherapp.exception.NotFoundUserException;
import com.acon.weatherapp.exception.NotMachedPasswordException;
import com.acon.weatherapp.repository.UserMapper;
import com.acon.weatherapp.session.UserSession;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;


import java.util.*;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserSession userSession;


    // 아이디 중복 회원 검증
    private void validateDuplicateUser(RegisterDto.Request dto) {
        userMapper.findByUserId(dto.getUserId())
                .ifPresent(user -> {
                    throw new DuplicateException("중복된 회원입니다.",dto);
                });
    }

    // 모든 유저 찾기 (사용자는 접근 x)
    public List<User> getAllUsers() {
        return userMapper.findAll();
    }
    /* 회원 가입 시 , 유효성 검사 및 중복 체크 */
//    public Map<String , String> validateHandling(Errors errors){
//        Map<String , String> validatorResult = new HashMap<>();
//
//        for(FieldError error : errors.getFieldErrors()){
//            String validKeyName = String.format("valid.%s", error.getField());
//            validatorResult.put(validKeyName, error.getDefaultMessage());
//        }
//        return validatorResult;
//    }

    // 회원 가입
    public RegisterDto.Response register(RegisterDto.Request dto) {
        if(!dto.getPassword().equals(dto.getConfirmPassword())){
            throw new NotMachedPasswordException("비밀번호 확인이 일치하지 않습니다.", dto);
        }
        validateDuplicateUser(dto);
        userMapper.save(dto.toEntity());

        return RegisterDto.Response.builder()
                .userId(dto.getUserId())
                .name(dto.getName())
                .build();
    }

    // 로그인 로직
    public LoginDto.Response login(LoginDto.Request dto) {

        LoginDto.Response result = userMapper.findByUserId(dto.getUserId())
                                    .filter(user -> dto.getPassword().equals(user.getPassword()))
                                    .map(LoginDto.Response::from)
                                    .orElseThrow(() -> new NotFoundUserException("입력한 회원을 찾을 수 없습니다." , dto));

        userSession.setUserId(result.getUserId());
        userSession.setRole(result.getRole());
        return result;
    }

    public UserInfoDto.Response getUserInfo(String userId) {

       return  userMapper
                .findByUserId(userId)
                .map(UserInfoDto.Response::from)
                .orElse(null);

    }
}
