package com.serjer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.serjer.model.User;
import com.serjer.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired 
	private UserService userService;
	

	@PostMapping("/register")
	public String userSingUp(@Valid @RequestBody User user) {
        
        return userService.registerUser(user);	
	}
	
	@PostMapping("/login")
	public String userSingIn(@Valid @RequestBody User user) {
		
        return userService.loginUser(user);	
	}

}
