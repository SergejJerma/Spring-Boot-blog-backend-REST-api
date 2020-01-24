package com.serjer.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	

	@GetMapping(value = "/users/{userId}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Post>> getPostsByUser(@PathVariable Long userId) {
		
		return new ResponseEntity<>(postService.getPostsByUserId(userId), HttpStatus.OK);
    }
	
	@PostMapping(value = "/users/{userId}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> addPost(@PathVariable Long userId, 
    		     @Valid @RequestBody Post post) {
	
        return new ResponseEntity<>(postService.addPostByUserId(userId, post), HttpStatus.OK);
    }
	
	@PutMapping(value = "/users/{userId}/posts/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<Post> updatePost(@PathVariable Long userId,
	                    	   @PathVariable Long postId,
	                    @Valid @RequestBody Post postUpdated) {

		 return new ResponseEntity<>(postService.updatePostByUserIdAndPostId(userId, postId, postUpdated), HttpStatus.OK);
	}
	
	@DeleteMapping("/users/{userId}/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long userId,
                             @PathVariable Long postId) {
    
        return new ResponseEntity<>(postService.deletePostByUserAndPostId(userId, postId), HttpStatus.OK);
    }
}
