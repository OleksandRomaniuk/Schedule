package com.schedule.proj.exсeption;



import javax.naming.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {


    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}