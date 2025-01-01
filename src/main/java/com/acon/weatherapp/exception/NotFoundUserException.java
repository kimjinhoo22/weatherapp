package com.acon.weatherapp.exception;

import com.acon.weatherapp.dto.LoginDto;

public class NotFoundUserException extends RuntimeException{
    private final LoginDto.Request dto;
    public NotFoundUserException(String message , LoginDto.Request dto) {
        super(message);
        this.dto = dto;
    }
    public LoginDto.Request getDto() {
        return dto;
    }
}
