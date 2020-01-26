package com.serjer.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serjer.model.Post;

public interface PostRepo extends JpaRepository<Post, Long> {

	List<Post> findByAuthorId(Long userId);
}
