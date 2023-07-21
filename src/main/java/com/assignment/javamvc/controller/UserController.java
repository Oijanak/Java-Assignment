package com.assignment.javamvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.assignment.javamvc.model.User;
import com.assignment.javamvc.service.UserService;
import com.assignment.javamvc.util.Sha256Hash;

import jakarta.servlet.http.HttpSession;

@Controller 
public class UserController {
	@Autowired
	private UserService userServ;
	@Autowired
	private Sha256Hash hash;
	
	//Post method for registering user
	@PostMapping("/register")
	public String registerUser(@ModelAttribute User user,Model model) {
		
		//Check if email already exist or not if exist send error message
		if(userServ.verifyEmail(user.getEmail())) {
			
			model.addAttribute("fname",user.getFname());
			model.addAttribute("lname",user.getLname());
			model.addAttribute("gender",user.getGender());
			model.addAttribute("email",user.getEmail());
			model.addAttribute("address",user.getAddress());
			model.addAttribute("phone",user.getPhone());
			model.addAttribute("error","Email alredy registerd");
			return "Register";
		}
		userServ.registerUser(user);
		return "Login";
		
	}
	
	//Post method for signing in
	@PostMapping("/login")
	public String loginUser(Model model,@RequestParam String email,@RequestParam String password,HttpSession session) {
		password=hash.hashString(password);
		//Checking if user exists or not
		if(userServ.verifyUser(email, password)) {
			session.setAttribute("active", userServ.getUser(email, password));
			session.setMaxInactiveInterval(-100);
			model.addAttribute("user",userServ.getUser(email, password));
			return "UserProfile";
		}
		model.addAttribute("email",email);
		model.addAttribute("error","email or password didn't match");
		return "Login";
	}
	
	@GetMapping("/userProfile")
	public String getUserProfile(HttpSession session,Model model) {
		
		User user=(User) session.getAttribute("active");
		if(user==null) {
			model.addAttribute("expired","Session Expired");
			return "Login";
		}
		model.addAttribute("user",userServ.getUserById(user.getId()));
		return "UserProfile";
	}
	
	@GetMapping("/changePassword")
	public String getChangePassword(HttpSession session) {
		if(session.getAttribute("active")==null) {
			return "Login";
		}
		return "ChangePassword";
	}
	
	
	//Change Password
	@PostMapping("/changePassword")
	public String changePassword(HttpSession session,@RequestParam String password,@RequestParam String newPassword,Model model) {
		User u=(User) session.getAttribute("active");
		if(u==null) {
			return "Login";
		}
		password=hash.hashString(password);
		System.out.println(u.getPassword());
		if(u.getPassword().equals(password)) {
			System.out.println(u.getId());
			u.setPassword(newPassword);
			userServ.registerUser(u);
			model.addAttribute("Success","Password Changed");
			return "ChangePassword";
		}
		
		model.addAttribute("unmatch","Password didnt match");
		return "ChangePassword";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "Login";
	}
	@GetMapping("/updateProfile")
	public String getUpdateForm(HttpSession session,Model model) {
		User user=(User) session.getAttribute("active");
		if(user==null) {
			return "Login";
		}
		model.addAttribute("user",user);
		return "UpdateProfile";
	}
	@PostMapping("/updateProfile")
	public String updateProfile(@ModelAttribute User user,Model model) {
		userServ.updateUser(user);
		model.addAttribute("user",userServ.getUserById(user.getId()));
		return "UserProfile";
	}
	
}
