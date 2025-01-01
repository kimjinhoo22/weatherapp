package com.acon.weatherapp.exception;


import com.acon.weatherapp.dto.RegisterDto;

public class DuplicateException extends RuntimeException {
    private final RegisterDto.Request dto;
    public DuplicateException(String message, RegisterDto.Request dto) {
        super(message);
        this.dto = dto;
    }

    public RegisterDto.Request getDto() {
        return dto;
    }
}
