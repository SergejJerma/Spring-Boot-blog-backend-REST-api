package com.serjer.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.serjer.exceptions.NotFoundException;
import com.serjer.model.Post;
import com.serjer.repos.PostRepo;
import com.serjer.repos.UserRepo;


@Service
public class PostService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	public List<Post> getPostsByUserId(Long userId) {
		userService.verifyUserExistenceInDb(userId);
	    	  
		return postRepo.findByAuthorId(userId);
	}

	public Post addPostByUserId(Long userId, @Valid Post post) {
		
		return userRepo.findById(userId)
				.map(user -> {
					post.setAuthor(user);
					return postRepo.save(post);
				}).orElseThrow(() -> new NotFoundException("User not found!"));
	}
	
	public Post updatePostByUserIdAndPostId(Long userId, Long postId, Post postUpdated) {
		userService.verifyUserExistenceInDb(userId);
	   
		return postRepo.findById(postId)
				.map(post ->{
					post.setTitle(postUpdated.getTitle());
					post.setText(postUpdated.getText());
					return postRepo.save(post);
				}).orElseThrow(() -> new NotFoundException("Post not found!"));
	}

	public String deletePostByUserAndPostId(Long userId, Long postId) {
		userService.verifyUserExistenceInDb(userId);
		
		return postRepo.findById(postId)
			    .map(post -> {
			        postRepo.delete(post);
			        return "Deleted Successfully!";
			    }).orElseThrow(() -> new NotFoundException("Post not found!"));
	}
	
}
