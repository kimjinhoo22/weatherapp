package com.acon.weatherapp.service;

import com.acon.weatherapp.dto.RegisterDto;
import com.acon.weatherapp.entity.User;
import com.acon.weatherapp.exception.DuplicateException;
import com.acon.weatherapp.repository.UserMapper;
import com.acon.weatherapp.session.UserSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
class UserServiceTest {
    @MockitoBean
    private UserMapper userMapper;

    @MockitoBean
    private UserSession userSession;

    @Autowired
    private UserService userService;



    @Test
    @DisplayName("모든 유저 반환 메서드")
    void getAllUsers() {

        User user1 = new User("김진후","wlsgnwkd22");
        User user2 = new User("최재욱","chlwdonr22");


        when(userMapper.findAll()).thenReturn(List.of(user1,user2));
        List<User> users = userService.getAllUsers();
        assertNotNull(users,"리스트는 null 이면 안됨");
        assertEquals(2,users.size());
        assertEquals("김진후",users.get(0).getName());
        assertEquals("최재욱",users.get(1).getName());

    }

    @Test
    void validDuplicateUser(){
        String userId = "wlsgnwkd22";
        RegisterDto.Request registerDto = new RegisterDto.Request(userId);

        User user = new User("김진후",userId);
        when(userMapper.findByUserId(userId)).thenReturn(Optional.of(user));

        DuplicateException exception = assertThrows(DuplicateException.class, ()->{
            userService.validateDuplicateUser(registerDto);
        });

        assertEquals("중복된 회원입니다.",exception.getMessage());
        assertEquals(registerDto , exception.getDto());

    }
    @Test
    @DisplayName("중복된 사용자가 아닐 때 검증 테스트")
    void validateNonDuplicateUserTest() {
        // Given
        String userId = "wlsgnwkd22";
        RegisterDto.Request dto = new RegisterDto.Request(userId); // 필요한 필드만 설정

        when(userMapper.findByUserId(userId)).thenReturn(Optional.empty()); // 중복 사용자 없음

        // When
        assertDoesNotThrow(() -> userService.validateDuplicateUser(dto)); // 예외가 발생하지 않아야 함
    }

    @Test
    void register() {
        User user = new User("김진후","wlsgnwkd22");
        User user2 = new User("최재욱","chlwodnr22");
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);


        UserService userService = new UserService(userMapper,userSession);


    }

    @Test
    void login() {
    }

    @Test
    void getUserInfo() {


    }
}