package com.shoppingcart.controllers.v1;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.shoppingcart.ShoppingCartApplication;
import com.shoppingcart.entities.User;
import com.shoppingcart.services.UserService;
import com.shoppingcart.template.UserTemplate;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, 
		classes = ShoppingCartApplication.class
		)
public class UserControllerTest {

	@InjectMocks
	UserController userController;
	
	@Mock
	UserService userService;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}
	
	private static final String URI_PATH = "/v1/user/";
	
	@Test
	public void testInsert_whenSucess_expectStatusCreated() throws Exception {
		String body = "{\"name\":\"Pedro\"}";
	
		new UserTemplate();
		User userInput = UserTemplate.insertUserTemplate();
		User userOutput = UserTemplate.getUserTemplate();
		
		when(userService.insertUser(userInput)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(userOutput));
		
		mockMvc.perform(post(URI_PATH).content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(".id").value("61ec5fc5cd357d1247bbae9d"))
                .andExpect(jsonPath(".name").value("Pedro"));
	
	}
	
	@Test
	public void testInsert_whenInternalServerError_expectStatusInternalServerError() throws Exception {
		String body = "{\"name\":\"Pedro\"}";
	
		new UserTemplate();
		User userInput = UserTemplate.insertUserTemplate();
		
		when(userService.insertUser(userInput)).thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
		
		mockMvc.perform(post(URI_PATH).content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
	
	}
	
	@Test
	public void testInsert_whenBadRequest_expectStatusBadRequest() throws Exception {
		String body = "{}";
	
		new UserTemplate();
		User userInput = UserTemplate.userBadRequestTemplate();
		
		when(userService.insertUser(userInput)).thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
		
		mockMvc.perform(post(URI_PATH).content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
	
	}
		
	@Test
	public void testFindById_whenSucess_expectStatusOK() throws Exception {
		String id = "61ec5fc5cd357d1247bbae9d";
		new UserTemplate();
		User user = UserTemplate.getUserTemplate();
		
		when(userService.findById(id)).thenReturn(ResponseEntity.ok().body(user));
		
		mockMvc.perform(get(URI_PATH + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(".id").value("61ec5fc5cd357d1247bbae9d"))
                .andExpect(jsonPath(".name").value("Pedro"));
		
	}
	
	@Test
	public void testFindById_whenNotFound_expectStatusNotFound() throws Exception {
		String id = "111111111111111111111111";
		
		when(userService.findById(id)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
		
		mockMvc.perform(get(URI_PATH + id))
		        .andDo(print())
		        .andExpect(status().isNotFound());
	}
	
	@Test
	public void testUpdate_whenSucess_expectStatusOK() throws Exception {
		String id = "61ec5fc5cd357d1247bbae9d";
		String body = "{\"name\":\"Bruno\"}";
	
		new UserTemplate();
		User userInput = UserTemplate.updateUserTemplate();
		User userOutput = UserTemplate.getUserUpdatedTemplate();
		
		when(userService.updateUser(id, userInput)).thenReturn(ResponseEntity.ok().body(userOutput));
		
		mockMvc.perform(put(URI_PATH + id).content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(".id").value("61ec5fc5cd357d1247bbae9d"))
                .andExpect(jsonPath(".name").value("Bruno"));
	
	}
	
	@Test
	public void testUpdate_whenInternalServerError_expectStatusInternalServerError() throws Exception {
		String id = "61ec5fc5cd357d1247bbae9d";
		String body = "{\"name\":\"Bruno\"}";
	
		new UserTemplate();
		User userInput = UserTemplate.updateUserTemplate();
		
		when(userService.updateUser(id, userInput)).thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
		
		mockMvc.perform(put(URI_PATH + id).content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
	
	}
	
	@Test
	public void testUpdate_whenBadRequest_expectStatusBadRequest() throws Exception {
		String id = "61ec5fc5cd357d1247bbae9d";
		String body = "{}";
	
		new UserTemplate();
		User userInput = UserTemplate.userBadRequestTemplate();
		
		when(userService.updateUser(id, userInput)).thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
		
		mockMvc.perform(put(URI_PATH + id).content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
	
	}
	
	@Test
	public void testUpdate_whenNotFound_expectStatusNotFound() throws Exception {
		String id = "111111111111111111111111";
		String body = "{\"name\":\"Bruno\"}";
	
		new UserTemplate();
		User userInput = UserTemplate.updateUserTemplate();
		
		when(userService.updateUser(id, userInput)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
		
		mockMvc.perform(put(URI_PATH + id).content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
	
	}
	
	@Test
	public void testDalete_whenSucess_expectStatusNoContent() throws Exception {
		String id = "61ec5fc5cd357d1247bbae9d";
		
		when(userService.deleteUser(id)).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).body(null));
		
		mockMvc.perform(delete(URI_PATH + id))
                .andDo(print())
                .andExpect(status().isNoContent());
	
	}
	
	@Test
	public void testDelete_whenInternalServerError_expectStatusInternalServerError() throws Exception {
		String id = "61ec5fc5cd357d1247bbae9d";
		
		when(userService.deleteUser(id)).thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
		
		mockMvc.perform(delete(URI_PATH + id))
                .andDo(print())
                .andExpect(status().isInternalServerError());
	
	}
	
	@Test
	public void testDelete_whenNotFound_expectStatusNotFound() throws Exception {
		String id = "111111111111111111111111";
		
		when(userService.deleteUser(id)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
		
		mockMvc.perform(delete(URI_PATH + id))
                .andDo(print())
                .andExpect(status().isNotFound());
	
	}
}
