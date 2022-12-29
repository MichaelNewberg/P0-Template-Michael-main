package com.revature.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoggingInterceptor implements HandlerInterceptor{


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //returns name of HTTP method (Put, Post, Get)
        MDC.put("METHOD", request.getMethod());
        //returns URI of http request
        MDC.put("URI", request.getRequestURI());
        System.out.println("Logging Interceptor preHandle executed.");
        return true;
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear(); //removes all key/value pairs, clearing it for next http request
        System.out.println("Logging Interceptor afterCompletion executed.");
    }
    
}

