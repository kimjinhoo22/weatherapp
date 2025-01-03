package com.acon.weatherapp.handler;


import com.acon.weatherapp.exception.DuplicateException;
import com.acon.weatherapp.exception.NotFoundUserException;
import com.acon.weatherapp.exception.NotMachedPasswordException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
//    @ExceptionHandler(IllegalArgumentException.class)
//    public String handlerIllegalArgument(IllegalArgumentException e, Model model) {
//
//        return "home";
//    }
//
//    @ExceptionHandler(IllegalStateException.class)
//    public String handlerIllegalState(IllegalStateException e, Model model) {
//        return "home";
//    }


    @ExceptionHandler(NotMachedPasswordException.class)
    public String handleNotMachedPasswordException(NotMachedPasswordException e , Model model) {
        model.addAttribute("message", e.getMessage());
        model.addAttribute("user", e.getDto());
        return "register";
    }

    @ExceptionHandler(DuplicateException.class)
    public String handlerDuplicateException(DuplicateException e, Model model ) {
        System.out.println(e.getDto());
        model.addAttribute("user", e.getDto());
        model.addAttribute("message", e.getMessage());
        return "register";
    }

    @ExceptionHandler(NotFoundUserException.class)
    public String handlerNotFoundUserException(Model model, NotFoundUserException e) {
        model.addAttribute("user", e.getDto());
        model.addAttribute("message", e.getMessage());
        return "home";
    }


}
