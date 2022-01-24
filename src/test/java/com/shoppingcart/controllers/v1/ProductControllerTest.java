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
import com.shoppingcart.entities.Product;
import com.shoppingcart.services.ProductService;
import com.shoppingcart.template.ProductTemplate;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, 
		classes = ShoppingCartApplication.class
		)
public class ProductControllerTest {

	@InjectMocks
	ProductController productController;
	
	@Mock
	ProductService productService;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}
	
	private static final String URI_PATH = "/v1/product/";
	
	@Test
	public void testInsert_whenSucess_expectStatusCreated() throws Exception {
		String body = "{\"name\":\"banana\",\"price\":10.0,\"quantity\":2,\"isActive\":true}";
	
		new ProductTemplate();
		Product productInput = ProductTemplate.insertProductTemplate();
		Product productOutput = ProductTemplate.getProductTemplate();
		
		when(productService.insertProduct(productInput)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(productOutput));
		
		mockMvc.perform(post(URI_PATH).content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(".id").value("61ebb454592cf360a063d4ba"))
                .andExpect(jsonPath(".name").value("banana"))
                .andExpect(jsonPath(".price").value(10.0))
                .andExpect(jsonPath(".quantity").value(2))
                .andExpect(jsonPath(".isActive").value(true));
	
	}
	
	@Test
	public void testInsert_whenInternalServerError_expectStatusInternalServerError() throws Exception {
		String body = "{\"name\":\"banana\",\"price\":10.0,\"quantity\":2,\"isActive\":true}";
	
		new ProductTemplate();
		Product productInput = ProductTemplate.insertProductTemplate();
		
		when(productService.insertProduct(productInput)).thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
		
		mockMvc.perform(post(URI_PATH).content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
	
	}
	
	@Test
	public void testInsert_whenBadRequest_expectStatusBadRequest() throws Exception {
		String body = "{\"price\":10.0,\"quantity\":2,\"isActive\":true}";
	
		new ProductTemplate();
		Product productInput = ProductTemplate.insertProductBadRequestTemplate();
		
		when(productService.insertProduct(productInput)).thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
		
		mockMvc.perform(post(URI_PATH).content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
	
	}
		
	@Test
	public void testFindById_whenSucess_expectStatusOK() throws Exception {
		String id = "61ebb454592cf360a063d4ba";
		new ProductTemplate();
		Product product = ProductTemplate.getProductTemplate();
		
		when(productService.findById(id)).thenReturn(ResponseEntity.ok().body(product));
		
		mockMvc.perform(get(URI_PATH + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(".id").value("61ebb454592cf360a063d4ba"))
                .andExpect(jsonPath(".name").value("banana"))
                .andExpect(jsonPath(".price").value(10.0))
                .andExpect(jsonPath(".quantity").value(2))
                .andExpect(jsonPath(".isActive").value(true));
		
	}
	
	@Test
	public void testFindById_whenNotFound_expectStatusNotFound() throws Exception {
		String id = "111111111111111111111111";
		
		when(productService.findById(id)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
		
		mockMvc.perform(get(URI_PATH + id))
		        .andDo(print())
		        .andExpect(status().isNotFound());
	}
	
	@Test
	public void testUpdate_whenSucess_expectStatusOK() throws Exception {
		String id = "61ebb454592cf360a063d4ba";
		String body = "{\"name\":\"banana\",\"price\":12.0,\"quantity\":4,\"isActive\":true}";
	
		new ProductTemplate();
		Product productInput = ProductTemplate.updateProductTemplate();
		Product productOutput = ProductTemplate.getProductUpdatedTemplate();
		
		when(productService.updateProduct(id, productInput)).thenReturn(ResponseEntity.ok().body(productOutput));
		
		mockMvc.perform(put(URI_PATH + id).content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(".id").value("61ebb454592cf360a063d4ba"))
                .andExpect(jsonPath(".name").value("banana"))
                .andExpect(jsonPath(".price").value(12.0))
                .andExpect(jsonPath(".quantity").value(4))
                .andExpect(jsonPath(".isActive").value(true));
	
	}
	
	@Test
	public void testUpdate_whenInternalServerError_expectStatusInternalServerError() throws Exception {
		String id = "61ebb454592cf360a063d4ba";
		String body = "{\"name\":\"banana\",\"price\":12.0,\"quantity\":4,\"isActive\":true}";
	
		new ProductTemplate();
		Product productInput = ProductTemplate.updateProductTemplate();
		
		when(productService.updateProduct(id, productInput)).thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
		
		mockMvc.perform(put(URI_PATH + id).content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
	
	}
	
	@Test
	public void testUpdate_whenBadRequest_expectStatusBadRequest() throws Exception {
		String id = "61ebb454592cf360a063d4ba";
		String body = "{\"price\":12.0,\"quantity\":4,\"isActive\":true}";
	
		new ProductTemplate();
		Product productInput = ProductTemplate.updateProductBadRequestTemplate();
		
		when(productService.updateProduct(id, productInput)).thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
		
		mockMvc.perform(put(URI_PATH + id).content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
	
	}
	
	@Test
	public void testUpdate_whenNotFound_expectStatusNotFound() throws Exception {
		String id = "111111111111111111111111";
		String body = "{\"name\":\"banana\",\"price\":12.0,\"quantity\":4,\"isActive\":true}";
	
		new ProductTemplate();
		Product productInput = ProductTemplate.updateProductTemplate();
		
		when(productService.updateProduct(id, productInput)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
		
		mockMvc.perform(put(URI_PATH + id).content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
	
	}
	
	@Test
	public void testDalete_whenSucess_expectStatusNoContent() throws Exception {
		String id = "61ebb454592cf360a063d4ba";
		
		when(productService.deleteProduct(id)).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).body(null));
		
		mockMvc.perform(delete(URI_PATH + id))
                .andDo(print())
                .andExpect(status().isNoContent());
	
	}
	
	@Test
	public void testDelete_whenInternalServerError_expectStatusInternalServerError() throws Exception {
		String id = "61ebb454592cf360a063d4ba";
		
		when(productService.deleteProduct(id)).thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
		
		mockMvc.perform(delete(URI_PATH + id))
                .andDo(print())
                .andExpect(status().isInternalServerError());
	
	}
	
	@Test
	public void testDelete_whenNotFound_expectStatusNotFound() throws Exception {
		String id = "111111111111111111111111";
		
		when(productService.deleteProduct(id)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
		
		mockMvc.perform(delete(URI_PATH + id))
                .andDo(print())
                .andExpect(status().isNotFound());
	
	}
}
