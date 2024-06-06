package com.yabeto.marvel.marvel_api.web;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.yabeto.marvel.marvel_api.entities.UserInteractionLog;
import com.yabeto.marvel.marvel_api.exception.ApiErrorException;
import com.yabeto.marvel.marvel_api.repositories.UserInteractionLogRepository;
import com.yabeto.marvel.marvel_api.services.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UserInteractionInterceptor implements HandlerInterceptor{


    @Autowired
    private UserInteractionLogRepository userInteractionLogRepository;

    @Autowired
    @Lazy
    private AuthenticationService authenticationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

                System.out.println("Interceptor: " + this.getClass().getName());

                String requestURI = request.getRequestURI();
                if(requestURI.startsWith("/comics") || request.getRequestURI().startsWith("/characters")){

                    UserInteractionLog userLog = new UserInteractionLog();
                    userLog.setHttpMethod(request.getMethod());
                    userLog.setUrl(request.getRequestURL().toString());
    
                    UserDetails user = authenticationService.getUserLoggedIn();
                    userLog.setUsername(user.getUsername());
                    userLog.setRemoteAddress(request.getRemoteAddr());
                    userLog.setTimestamp(LocalDateTime.now());
    
                    try {
                        userInteractionLogRepository.save(userLog);
                        return true;
                    } catch (Exception e) {
                        throw new ApiErrorException("No se logro guardar el registro de interaccion correctamente");
                    }
                }
                return true;
               
    }
    

}
