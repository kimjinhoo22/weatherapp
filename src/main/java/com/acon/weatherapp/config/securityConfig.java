package com.acon.weatherapp.config;

import com.acon.weatherapp.handler.CustomAuthfailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class securityConfig {



    @Bean
    AuthenticationFailureHandler customAuthFailureHandler() {
        return new CustomAuthfailureHandler();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/","/user/register","/user/password").permitAll()
                .requestMatchers("/user/my-page").hasRole("USER")
                .anyRequest().authenticated()
        );

        http.formLogin(form -> form
                        .loginPage("/")
                        .loginProcessingUrl("/login")
                        .failureHandler(new CustomAuthfailureHandler()))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll());

        http
                .csrf((csrf) -> csrf.disable());

        return http.build();
    }

}
