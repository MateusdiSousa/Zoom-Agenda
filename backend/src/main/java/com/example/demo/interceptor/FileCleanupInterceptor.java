package com.example.demo.interceptor;

import java.io.File;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


//Creating a component to delete the temporary copy from meeting attachment
@Component
public class FileCleanupInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception exception) {

        // When the response to client has tempFilePath atribute, then the interceptor do this action
        String tempFilePath = (String) request.getAttribute("tempFilePath");

        if (tempFilePath != null) {
            File tempFile = new File(tempFilePath);

            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}
