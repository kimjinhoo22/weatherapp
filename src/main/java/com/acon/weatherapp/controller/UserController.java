package com.acon.weatherapp.controller;
import com.acon.weatherapp.dto.LoginDto;
import com.acon.weatherapp.dto.RegisterDto;
import com.acon.weatherapp.dto.UserInfoDto;
import com.acon.weatherapp.entity.User;
import com.acon.weatherapp.service.UserService;
import com.acon.weatherapp.session.UserSession;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserSession userSession;


    @PostMapping("/login")
    public String login(@ModelAttribute LoginDto.Request dto) {
        LoginDto.Response user = userService.login(dto);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> listG(Model model) {
        List<User> list =  userService.getAllUsers();

        return ResponseEntity.status(200).body(list);
    }

    @GetMapping("/register")
    public String registerG() {
        return "register";
    }

    @GetMapping("/password")
    public String findG() {
        return "findPassword";
    }

    @PostMapping("/password")
    public String findP(@RequestParam String userId ,Model model) {
        boolean result = userService.findPassword(userId);
        if(result){
            return "updatePassword";
        }
        return "findPassword";
    }

    @PostMapping("/register")
    public String registerP(@Valid @ModelAttribute RegisterDto.Request dto , Errors errors , Model model) {
        // 유효성검사 에러 존재시 진행 ->

         if(errors.hasErrors()) {
             userService.errorsHandler(errors,model);
             model.addAttribute("user" , dto);
             return "register";
         }

         // 유효성검사 에러가 없다면 진행 ->
        RegisterDto.Response userInfo = userService.register(dto);
            model.addAttribute("userInfo"  , userInfo);
            return "regisSuccess";

    }

    @GetMapping("/my-page")
    public String my_page(Model model){
        String userId = userSession.getUserId();
        UserInfoDto.Response user  = userService.getUserInfo(userId);
        model.addAttribute("userInfo", user);
        return "my-page";
    }

    @GetMapping("/my-page/edit")
    public String my_page_edit( Model model){
        String userId = userSession.getUserId();
        UserInfoDto.Response userInfo = userService.getUserInfo(userId);
        model.addAttribute("userInfo", userInfo);
        return "my-page-edit";
    }

    @PostMapping("/my-page/edit")
    public String my_page_edit_P(@ModelAttribute UserInfoDto.Request dto , Model model){
        userService.updateUser(dto);
        return "redirect:/user/my-page";
    }


}
