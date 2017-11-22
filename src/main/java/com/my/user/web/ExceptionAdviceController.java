package com.my.user.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.util.NestedServletException;

@ControllerAdvice
public class ExceptionAdviceController {

	@ExceptionHandler({ AuthenticationException.class })
	public ModelAndView authenticationError(AuthenticationException e) throws IOException {
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("message", getExceptionMessage(e));
		return mv;
	}

	@ExceptionHandler(Throwable.class)
	public ModelAndView handle500Error(HttpServletRequest request, Exception e) {
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("message", getExceptionMessage(e));
		return mv;
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView handle404Error(HttpServletRequest request, Exception e) {
		ModelAndView mv = new ModelAndView("404");
		mv.addObject("message", getExceptionMessage(e));
		return mv;
	}
	
	@ExceptionHandler(NestedServletException.class)
	public ModelAndView hanleFreemarkerError(HttpServletRequest request, Exception e) {
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("message", this.getExceptionMessage(e));
		return mv;
	}
	
	
	private String getExceptionMessage(Throwable e) {
		e.printStackTrace();
		StringBuilder sb = new StringBuilder();
		do {
			sb.append(e.getMessage()).append(e.getCause() != null ? "\ncause:" : "");
		}while((e=e.getCause()) != null);
		return sb.toString();
	}
}
