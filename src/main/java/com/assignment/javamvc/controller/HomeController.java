package com.assignment.javamvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping({"/","/login"})
	public String welcome() {
		return "Login";
	}
	@GetMapping("/register")
	public String register() {
		return "Register";
	}
}
