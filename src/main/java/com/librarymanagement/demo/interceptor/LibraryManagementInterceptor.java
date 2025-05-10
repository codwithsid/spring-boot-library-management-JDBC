package com.librarymanagement.demo.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class LibraryManagementInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LibraryManagementInterceptor.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        String clientIp = request.getRemoteAddr();
        String dateTime = LocalDateTime.now().format(formatter);

        logger.info("Inside Interceptor -> preHandle() :: Request URL: {} :: Query String: {} :: Client IP: {} :: Received on: {} :: Start Time: {}",
                request.getRequestURL(), request.getQueryString(), clientIp, dateTime, startTime);

        request.setAttribute("startTime", startTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String dateTime = LocalDateTime.now().format(formatter);
        logger.info("Inside Interceptor -> postHandle() :: Request URL: {} :: Handled at: {} :: Current Time: {}",
                request.getRequestURL(), dateTime, System.currentTimeMillis());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        String dateTime = LocalDateTime.now().format(formatter);

        logger.info("Inside Interceptor -> afterCompletion() :: Request URL: {} :: Completed at: {} :: End Time: {}",
                request.getRequestURL(), dateTime, endTime);
        logger.info("Request URL: {} :: Total Time Taken = {} ms", request.getRequestURL(), (endTime - startTime));
    }
}
