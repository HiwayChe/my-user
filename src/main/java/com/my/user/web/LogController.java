package com.my.user.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/log")
public class LogController {

	@RequestMapping("")
	public String index() {
		return "log/log";
	}
}
