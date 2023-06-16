package com.tjoeun.project.exception;

import javax.servlet.http.HttpServlet;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class DefaultExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandler(HttpServlet req, Exception ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("exception", ex);
        return mav;
    }
}