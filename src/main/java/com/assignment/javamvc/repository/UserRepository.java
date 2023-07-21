package com.assignment.javamvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.javamvc.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	//Checks if User with the email and password exists in database and returns User object if exists otherwise returns null
	User findUserByEmailAndPassword(String email,String password);
	//Check if email already exist in database or not
	User findUserByEmail(String email);
	

}
