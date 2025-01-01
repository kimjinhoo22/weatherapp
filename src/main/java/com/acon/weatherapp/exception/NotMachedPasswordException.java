package com.acon.weatherapp.exception;


import com.acon.weatherapp.dto.RegisterDto;

public class NotMachedPasswordException extends RuntimeException {
    private final RegisterDto.Request dto;
    public NotMachedPasswordException(String message, RegisterDto.Request dto) {
        super(message);
        this.dto = dto;
    }

    public RegisterDto.Request getDto() {
        return dto;
    }
}
