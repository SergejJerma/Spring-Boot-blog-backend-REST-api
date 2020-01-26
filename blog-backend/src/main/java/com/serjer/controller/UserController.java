package com.serjer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


	@PostMapping("/singup")
	public ResponseEntity<String> userSingUp(@Valid @RequestBody User user) {
        
        return new ResponseEntity<>(userService.registerUser(user), HttpStatus.OK);	
	}
	
	@PostMapping("/singin")
	public ResponseEntity<String> userSingIn(@Valid @RequestBody User user) {
		
        return new ResponseEntity<>(userService.loginUser(user), HttpStatus.OK);	
	}

}
