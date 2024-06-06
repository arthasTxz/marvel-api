package com.yabeto.marvel.marvel_api.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.yabeto.marvel.marvel_api.services.JwtService;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
                String authorizationHeader = request.getHeader("Authorization");

                if(!org.springframework.util.StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")){
                    filterChain.doFilter(request, response);
                    return;
                }

                String jwt = authorizationHeader.split(" ")[1];
                String subject = null;

                try {
                    subject = jwtService.extractSubject(jwt);
                } catch (JwtException jwtException) {
                    filterChain.doFilter(request, response);
                    return;
                }
                UserDetails user = userDetailsService.loadUserByUsername(subject);
                System.out.println(user);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
    }

}
