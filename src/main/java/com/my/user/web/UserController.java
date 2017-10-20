package com.my.user.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.user.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/dologin", produces = { "text/plain; charset=UTF-8" })
	@ResponseBody
	public String doLogin(String name, String password) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(name, password.toCharArray());
		try {
			subject.login(token);// 会跳到我们自定义的realm中
			//request.getSession().setAttribute("user", user);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			//request.getSession().setAttribute("user", user);
			//request.setAttribute("error", "用户名或密码错误！");
			return "login";
		}
	}

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
