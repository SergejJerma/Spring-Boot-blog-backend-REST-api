package com.serjer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.serjer.controller.PostController;
import com.serjer.model.Post;
import com.serjer.service.PostService;
import com.serjer.service.UserService;

@WebMvcTest(value = PostController.class)
class PostControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PostService postService;
	
	@MockBean
	private UserService userService;
	
	List<Post> mockListOfPostsByUser =  new ArrayList<>(Arrays.asList(
												new Post(1L, "Pirmas", "Pirmas tekstas"),
												new Post(2L, "Antras", "Antras tekstas"),
												new Post(3L, "Trecias", "Trecias tekstas")));
	Post postFromDb = new Post(1L, "Testas", "Tekstas idetas");
	String examplePostJson = "{\"title\":\"Testas\",\"text\":\"Tekstas idetas\"}";
	String message = "Deleted!";
	
	@Test
	void getPostsByUserTest() throws Exception {
		
		Mockito.when(postService.getPostsByUserId(Mockito.anyLong()))
								.thenReturn(mockListOfPostsByUser);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
											.get("/api/users/1/posts")
											.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	
		String expected = "[{\"id\":1,\"title\":\"Pirmas\",\"text\":\"Pirmas tekstas\"},"
						 + "{\"id\":2,\"title\":\"Antras\",\"text\":\"Antras tekstas\"},"
						 + "{\"id\":3,\"title\":\"Trecias\",\"text\":\"Trecias tekstas\"}]";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	void addPostTest() throws Exception {
			
		Mockito.when(postService.addPostByUserId(Mockito.anyLong(), Mockito.any(Post.class)))
								.thenReturn(postFromDb);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
											.post("/api/users/3/posts")
											.accept(MediaType.APPLICATION_JSON)
											.content(examplePostJson)
											.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			
		String expected = "{\"id\":1,\"title\":\"Testas\",\"text\":\"Tekstas idetas\"}";
		
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	void updatePostTest() throws Exception {
			
		Mockito.when(postService.updatePostByUserIdAndPostId(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(Post.class)))
								.thenReturn(postFromDb);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
											.put("/api/users/3/posts/1")
											.accept(MediaType.APPLICATION_JSON)
											.content(examplePostJson)
											.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expected = "{\"id\":1,\"title\":\"Testas\",\"text\":\"Tekstas idetas\"}";
		
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	void deletePostTest() throws Exception {
			
		Mockito.when(postService.deletePostByUserAndPostId(Mockito.anyLong(), Mockito.anyLong()))
								.thenReturn(message);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
											.delete("/api/users/3/posts/1")
											.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expected = "Deleted!";
		
		assertEquals(expected, result.getResponse().getContentAsString());
	}

}
