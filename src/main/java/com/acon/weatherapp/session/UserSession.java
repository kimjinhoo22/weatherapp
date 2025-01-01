package com.acon.weatherapp.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;



@Component
@NoArgsConstructor
@AllArgsConstructor
@SessionScope
@Getter
@Setter
public class UserSession {
    private String userId;
    private String role;
}
