package com.yabeto.marvel.marvel_api.exception;

public class ApiErrorException extends RuntimeException{

    public ApiErrorException(){
        super();
    }

    public ApiErrorException(final String message){
        super(message);
    }
    
    public ApiErrorException(String message, Throwable cause){
        super(message, cause);
    }

    public ApiErrorException(Throwable cause){
        super(cause);
    }

}
