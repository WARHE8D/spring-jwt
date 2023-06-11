package com.warhead.security.config;

import com.warhead.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

private final UserRepository repo;
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> repo
                .findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("user not found"));
    }
    @Bean//beans should always be public
    public AuthenticationProvider authProvider(){

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());//can change to custom enc
        authProvider.setUserDetailsService(userDetailsService());//idk why how
        return authProvider;
    }
}
