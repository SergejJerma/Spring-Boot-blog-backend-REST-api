package com.serjer;

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
	
	@Test
	public void getPostsByUserTest() throws Exception {
		List<Post> mockListOfPostsByUser =  new ArrayList<>(Arrays.asList(
															new Post(Long.valueOf(1), "Pirmas", "Pirmas tekstas"),
															new Post(Long.valueOf(2), "Antras", "Antras tekstas"),
															new Post(Long.valueOf(3), "Trecias", "Trecias tekstas")));
		
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
	public void addPostTest() throws Exception {
	
		Post enteredPost = new Post("Testas", "Tekstas idetas");
		String examplePostJson = "{\"title\":\"Testas\",\"text\":\"Tekstas idetas\"}";
		
		Mockito.when(postService.addPostByUserId(Mockito.anyLong(), Mockito.any(Post.class)))
								.thenReturn(enteredPost);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
											.post("/api/users/3/posts")
											.accept(MediaType.APPLICATION_JSON)
											.content(examplePostJson)
											.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		
		String expected = "{\"id\":null,\"title\":\"Testas\",\"text\":\"Tekstas idetas\"}";
		
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}

}
