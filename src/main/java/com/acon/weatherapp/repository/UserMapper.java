package com.acon.weatherapp.repository;

import com.acon.weatherapp.dto.RegisterDto;
import com.acon.weatherapp.dto.UserInfoDto;
import com.acon.weatherapp.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO users (userid, name, birth, password, gender, address, phone, role) " +
            "VALUES (#{userId}, #{name}, #{birth}, #{password}, #{gender}, #{address}, #{phone}, #{role})")
    void save(User user);

    @Select("SELECT * FROM users WHERE userid = #{userId}")
    Optional<User> findByUserId(String userId);

    @Select("SELECT * FROM users")
    List<User> findAll();



}
