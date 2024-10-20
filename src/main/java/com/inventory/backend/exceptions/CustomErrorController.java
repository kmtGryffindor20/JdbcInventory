package com.inventory.backend.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleNotFoundError(Exception ex) {
        ModelAndView mav = new ModelAndView("notfound");
        mav.addObject("message", ex.getMessage());
        return mav;
    }
}
