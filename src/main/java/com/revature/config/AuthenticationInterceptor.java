package com.revature.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.revature.exceptions.AuthenticationFailed;


@Component
public class AuthenticationInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //returns session object from request
        HttpSession session = request.getSession();
        //checking if session object is a valid instance
        if(session.getAttribute("user") != null){
            return true;
        }else{
            throw new AuthenticationFailed("Login required for access.");
        }
    }
    
}
