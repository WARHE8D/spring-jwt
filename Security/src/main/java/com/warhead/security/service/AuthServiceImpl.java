package com.warhead.security.service;

import com.warhead.security.config.JwtService;
import com.warhead.security.model.Role;
import com.warhead.security.model.User;
import com.warhead.security.repository.UserRepository;
import com.warhead.security.requestbody.AuthResponse;
import com.warhead.security.requestbody.AuthenticationReqBody;
import com.warhead.security.requestbody.RegisterReqBody;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authMgr;
    @Override
    public AuthResponse register(RegisterReqBody request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(Role.USER)
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .build();
        repo.save(user);

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthResponse authenticate(AuthenticationReqBody request) {
        authMgr.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repo.findByUsername(request.getUsername())
                .orElseThrow();
        String token = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }
}
