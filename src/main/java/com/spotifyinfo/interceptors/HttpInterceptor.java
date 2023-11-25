package com.spotifyinfo.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class HttpInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(HttpInterceptor.class);
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.addHeader("Access-Control-Allow-Headers", "*");
        response.addHeader("Access-Control-Max-Age", "86400");

        logger.info("[REQUEST] " + request.getMethod() + " " + request.getRequestURL() + " - " + request.getQueryString());
        return true;
    }
}
