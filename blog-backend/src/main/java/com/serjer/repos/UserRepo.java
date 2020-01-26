package com.serjer.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serjer.model.User;

public interface UserRepo extends JpaRepository<User, Long> {

	User findByEmail(String email);
}
