package com.assignment.javamvc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.javamvc.model.User;
import com.assignment.javamvc.repository.UserRepository;
import com.assignment.javamvc.service.UserService;
import com.assignment.javamvc.util.Sha256Hash;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private Sha256Hash hash;
	@Override
	public void registerUser(User user) {
		//Hashing password using SHA256 algorithm
		user.setPassword(hash.hashString(user.getPassword()));
		//Saving user data in database
		userRepo.save(user);
	}
	
	//Verify user in database
	@Override
	public boolean verifyUser(String email, String password) {
		
		if(userRepo.findUserByEmailAndPassword(email, password)!=null) {
			return true;
		}
		return false;
	}
	
	//Verify Duplicate email in database
	@Override
	public boolean verifyEmail(String email) {
		if(userRepo.findUserByEmail(email)!=null) {
			return true;
		}
		return false;
	}
	

}
