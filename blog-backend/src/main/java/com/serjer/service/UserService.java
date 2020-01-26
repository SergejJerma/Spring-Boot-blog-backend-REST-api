package com.serjer.service;

import java.security.Principal;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.serjer.exceptions.PermissionGetDataException;
import com.serjer.model.Role;
import com.serjer.model.User;
import com.serjer.repos.UserRepo;

@Primary
@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepo userRepo;
	    
	@Autowired
	private PasswordEncoder passwordEncoder;

	
    public String registerUser(User user) {

        if (loadUserByUsername(user.getEmail()) != null) 
        	return "User exits!";
          
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);

        	return "User singed up!";

    }

	public String loginUser(User user) {
		UserDetails userFromDb = loadUserByUsername(user.getEmail());
        if (userFromDb == null) 
        	return "User not yet registered!";
        else if (passwordEncoder.matches(user.getPassword(), userFromDb.getPassword())) 
        	return "You are singed in!";
        else 
        	return "Bad credentials!";
	
	}
	
	public User getUserById(Long userId) {
		return userRepo.findById(userId).get();
		
	}
	
	public boolean verifyUserExistenceInDb(Long userId) {
		return userRepo.existsById(userId);	
	}
	
	public void checkCurrentUserPermissionById(Long userId, Principal principal) {
		
		if(loadUserByUsername(principal.getName()).getId() != userId)
			throw new PermissionGetDataException("You have permission to get only your data, please, check your request"); 
	}

	@Override
	public User loadUserByUsername(String email) throws UsernameNotFoundException {
		 User user = userRepo.findByEmail(email);
		
		     if (user == null) new UsernameNotFoundException(email+ ": User not found");
	     
	     return user;
	}



	
}
