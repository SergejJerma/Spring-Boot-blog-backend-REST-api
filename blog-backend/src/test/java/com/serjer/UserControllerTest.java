package com.serjer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.serjer.controller.UserController;
import com.serjer.model.User;
import com.serjer.service.UserService;

@WebMvcTest(value = UserController.class)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;
	
	String message1 = "User exits!"; 
	String message2 = "User not yet registered!";
	String exampleUserJson = "{\"email\":\"test@test.com\",\"password\":\"testas\"}";
	
	@Test
	void userSingUpTest() throws Exception {
				
		Mockito.when(userService.registerUser(Mockito.any(User.class))).thenReturn(message1);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
												.post("/api/register")
												.accept(MediaType.APPLICATION_JSON)
												.content(exampleUserJson)
												.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expected = "User exits!";
		
		assertEquals(expected, result.getResponse().getContentAsString());

	}

	@Test
	void userSingInTest() throws Exception {
			
		Mockito.when(userService.loginUser(Mockito.any(User.class))).thenReturn(message2);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
												.post("/api/login")
												.accept(MediaType.APPLICATION_JSON)
												.content(exampleUserJson)
												.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expected = "User not yet registered!";
		
		assertEquals(expected, result.getResponse().getContentAsString());
	}

}
