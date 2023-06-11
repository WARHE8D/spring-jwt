package com.warhead.security.config;

import com.warhead.security.model.User;
import com.warhead.security.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtSer;
    private final UserService userService;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        //first thing in this filter is to check weather the request contains token or not
        //token are always passed in headers
        final String authHeader = request.getHeader("AuthVal");
        final String jwt;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response); //if no token call to next filter idk y
            return;
        }
        jwt = authHeader.substring(7);//"Bearing " is 8 chars ie index 7 so after this string we have the actual token
        String username = jwtSer.getUsername(jwt);
        //SecurityContextHolder.getContext().getAuthentication() checks if the user is authentication is done previously
        // if already done we dont need to perform it again
        if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null){
            User user = userService.getUserByUsername(username);
            if(jwtSer.isTokenValid(jwt,user)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken
                        (user,null,user.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            filterChain.doFilter(request,response);//must so that it can execute next filters
        }
    }
}
