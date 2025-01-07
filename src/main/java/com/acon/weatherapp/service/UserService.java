package com.acon.weatherapp.service;

import com.acon.weatherapp.dto.LoginDto;
import com.acon.weatherapp.dto.RegisterDto;
import com.acon.weatherapp.dto.UserInfoDto;
import com.acon.weatherapp.entity.CustomUserDetails;
import com.acon.weatherapp.entity.User;
import com.acon.weatherapp.exception.DuplicateException;
import com.acon.weatherapp.exception.NotFoundUserException;
import com.acon.weatherapp.exception.NotMachedPasswordException;
import com.acon.weatherapp.repository.UserMapper;
import com.acon.weatherapp.session.UserSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;
    private final UserSession userSession;

    public boolean findPassword(String userId){
       return userMapper.existsByUserId(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userMapper.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new CustomUserDetails(user);
    }

    // 아이디 중복 회원 검증
   public void validateDuplicateUser(RegisterDto.Request dto) {
        userMapper.findByUserId(dto.getUserId())
                .ifPresent(user -> {
                    throw new DuplicateException("중복된 회원입니다.",dto);
                });
    }
    // 모든 유저 찾기 (사용자는 접근 x)
    public List<User> getAllUsers() {
        return userMapper.findAll();
    }

    // 회원 가입
    @Transactional
    public RegisterDto.Response register(RegisterDto.Request dto) {
        if(!dto.getPassword().equals(dto.getConfirmPassword())){
            throw new NotMachedPasswordException("비밀번호 확인이 일치하지 않습니다.", dto);
        }
        validateDuplicateUser(dto);
        dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        dto.setRole("ROLE_USER");
        userMapper.save(dto.toEntity());

        return RegisterDto.Response.builder()
                .userId(dto.getUserId())
                .name(dto.getName())
                .build();
    }

    // 로그인 로직
//    public LoginDto.Response login(LoginDto.Request dto) {
//
//        LoginDto.Response result = userMapper.findByUserId(dto.getUserId())
//                                    .filter(user -> dto.getPassword().equals(user.getPassword()))
//                                    .map(LoginDto.Response::from)
//                                    .orElseThrow(() -> new NotFoundUserException("입력한 회원을 찾을 수 없습니다." , dto));
//
//        userSession.setUserId(result.getUserId());
//        userSession.setRole(result.getRole());
//        return result;
//    }

    public UserInfoDto.Response getUserInfo(String userId) {

       return  userMapper
                .findByUserId(userId)
                .map(UserInfoDto.Response::from)
                .orElse(null);

    }

    public void updateUser(UserInfoDto.Request dto) {
         userMapper.update(dto.toEntity());

    }

    public void updatePassword(String userId, String oldPassword, String newPassword) {
            User user = userMapper.findByUserId(userId)
                    .orElseThrow(()-> new NotFoundUserException(userId));
            if(!user.getPassword().equals(oldPassword)){
                throw new NotMachedPasswordException("기존 비밀번호와 같지 않습니다.");
            }
            user.setPassword(newPassword);
            userMapper.save(user);
    }
}
