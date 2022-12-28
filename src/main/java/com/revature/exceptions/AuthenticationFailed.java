package com.revature.exceptions;

public class AuthenticationFailed extends RuntimeException{
    public AuthenticationFailed(String message){
        super(message);
    }
}
