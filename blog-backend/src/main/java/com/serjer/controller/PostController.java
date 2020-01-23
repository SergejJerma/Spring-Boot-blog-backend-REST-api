package com.serjer.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.serjer.model.Post;
import com.serjer.service.PostService;

@RestController
@RequestMapping("/api")
public class PostController {
	
	
	@Autowired
	private PostService postService;
	
	
	@GetMapping("/users/{userId}/posts")
    public List<Post> getPostsByUser(@PathVariable Long userId) {
      
		return postService.getPostsByUserId(userId);
    }
	
	@PostMapping(value = "/users/{userId}/posts")
    public Post addPost(@PathVariable Long userId, 
    		     @Valid @RequestBody Post post) {
	
        return postService.addPostByUserId(userId, post);
    }
	
	@PutMapping("/users/{userId}/posts/{postId}")
	    public Post updatePost(@PathVariable Long userId,
	                    	   @PathVariable Long postId,
	                    @Valid @RequestBody Post postUpdated) {

		 return postService.updatePostByUserIdAndPostId(userId, postId, postUpdated);
	}
	
	@DeleteMapping("/users/{userId}/posts/{postId}")
    public String deletePost(@PathVariable Long userId,
                             @PathVariable Long postId) {
    
        return postService.deletePostByUserAndPostId(userId, postId);
    }
}
