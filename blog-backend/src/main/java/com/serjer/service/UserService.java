package com.serjer.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.serjer.exceptions.NotFoundException;
import com.serjer.model.Role;
import com.serjer.model.User;
import com.serjer.repos.UserRepo;


@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepo userRepo;
	    
	@Autowired
	private PasswordEncoder passwordEncoder;

	
    public String registerUser(User user) {

        if (isUserEmailInDb(user) != null) 
        	return "User exits!";
          
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);

        	return "User singed up!";

    }

	public String loginUser(User user) {
	
        if (isUserEmailInDb(user) == null) 
        	return "User not yet registered!";
        else if (passwordEncoder.matches(user.getPassword(), isUserEmailInDb(user).getPassword())) 
        	return "You are singed in!";
        else 
        	return "Invalid login data!";
	
	}
	
	private User isUserEmailInDb(User user) {
		return userRepo.findByEmail(user.getEmail());
	}
	
	public void verifyUserExistenceInDb(Long userId) {
		if(!userRepo.existsById(userId)) throw new NotFoundException("User not found!");
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		 User user = userRepo.findByEmail(email);

	     if (user == null) throw new UsernameNotFoundException("User not found");
	  
	     return user;
	}

}
