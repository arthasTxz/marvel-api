package com.yabeto.marvel.marvel_api.exception;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiErrorDto> handlerGeneralExceptions(
        Exception exception, HttpServletRequest request, WebRequest webRequest){

            if(exception instanceof HttpClientErrorException){
                return this.handleHttpClientErrorException((HttpClientErrorException)exception, request, webRequest);
            } else if(exception instanceof AccessDeniedException){
                return this.handleAccessDeniedException((AccessDeniedException)exception, request, webRequest);
            }else if(exception instanceof AuthenticationCredentialsNotFoundException){
                return this.handleAuthenticationCredentialsNotFoundException((AuthenticationCredentialsNotFoundException)exception, request, webRequest);
            }else{
                return this.handlerGenericException(exception, request, webRequest);
            }
        }

    private ResponseEntity<ApiErrorDto> handlerGenericException(
        Exception exception, 
        HttpServletRequest request,
        WebRequest webRequest) {
        
            ApiErrorDto dto = new ApiErrorDto(
                "Error inesperado, vuleva a intentarlo",
                 exception.getMessage(), 
                 request.getMethod(), 
                 request.getRequestURL().toString());
        return ResponseEntity.internalServerError().body(dto);
    }

    private ResponseEntity<ApiErrorDto> handleAuthenticationCredentialsNotFoundException(
        AuthenticationCredentialsNotFoundException exception,
        HttpServletRequest request, 
        WebRequest webRequest) {
            ApiErrorDto dto = new ApiErrorDto(
                "No tienes acceso a este recurso",
                 exception.getMessage(), 
                 request.getMethod(), 
                 request.getRequestURL().toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(dto);
    }

    private ResponseEntity<ApiErrorDto> handleAccessDeniedException(
        AccessDeniedException exception, 
        HttpServletRequest request,
        WebRequest webRequest) {
            ApiErrorDto dto = new ApiErrorDto(
                "No tienes acceso a este recurso",
                 exception.getMessage(), 
                 request.getMethod(), 
                 request.getRequestURL().toString());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(dto);
    }

    private ResponseEntity<ApiErrorDto> handleHttpClientErrorException(
        HttpClientErrorException exception, 
        HttpServletRequest request,
        WebRequest webRequest) {

            String message = null;

            if(exception instanceof HttpClientErrorException.Forbidden){
                message = "No tienes acceso a este recurso";
            }else if(exception instanceof HttpClientErrorException.Unauthorized){
                message = "No haz iniciado sesión para acceder a este recurso";
            }else if(exception instanceof HttpClientErrorException.NotFound){
                message = "Recurso no encontrado";
            }else if(exception instanceof HttpClientErrorException.Conflict){
                message = "Conflicto con el procesamiento de la petición";
            }else{
                message = "Error inesperado al realizar consulta";
            }

            ApiErrorDto dto = new ApiErrorDto(
                message,
                 exception.getMessage(), 
                 request.getMethod(), 
                 request.getRequestURL().toString());
        return ResponseEntity.status(exception.getStatusCode()).body(dto);
    }
}
