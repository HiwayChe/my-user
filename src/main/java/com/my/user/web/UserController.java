package com.my.user.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.user.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	private Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@ExceptionHandler(Exception.class)
	public String loginFail(Exception e) {
		e.printStackTrace();
		return "direct:/user/login";
	}
	
	@RequestMapping("/login")
	public String login() {
		log.info("hello");
		return "user/login";
	}

//	@RequestMapping(value = "/dologin", produces = { "text/plain; charset=UTF-8" })
//	@ResponseBody
//	public String doLogin(String name, String password) {
//		Subject subject = SecurityUtils.getSubject();
//		UsernamePasswordToken token = new UsernamePasswordToken(name, password.toCharArray());
//		try {
//			subject.login(token);// 会跳到我们自定义的realm中
//			//request.getSession().setAttribute("user", user);
//			return "success";
//		} catch (Exception e) {
//			e.printStackTrace();
//			//request.getSession().setAttribute("user", user);
//			//request.setAttribute("error", "用户名或密码错误！");
//			return "login";
//		}
//	}

	@RequestMapping("/index")
	@ResponseBody
	public String index() {
		return "index page";
	}

	@RequestMapping("/error")
	@ResponseBody
	public String error() {
		return "login error";
	}
}
