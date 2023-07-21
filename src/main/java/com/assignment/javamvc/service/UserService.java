package com.assignment.javamvc.service;

import com.assignment.javamvc.model.User;


public interface UserService {
	void registerUser(User user);
	boolean verifyUser(String email,String password);
	boolean verifyEmail(String email);

}
