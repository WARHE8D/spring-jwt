package com.warhead.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authProvider;
    private final JwtAuthenticationFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()//to disable csrf
                .disable()
                .authorizeHttpRequests()//for some request we dont need to authenticate tokens like in login or creating a new account so we whitelist those
                .requestMatchers("/api/v1/auth")
                .permitAll()//permits all matchers without authenticating
                .anyRequest()// and all the rest of the requests
                .authenticated()//needs to be authenticated
        //once per request means for every request we verify the request
        //ie we should not store the auth in session
        //to make that we need to put the session in stateless
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authProvider)
                //here we want to execute jwtFilter before usernamepwdAuthFilter
                //as we first validate user and then update the auth refer JwtAuthenticationFilter.class
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
