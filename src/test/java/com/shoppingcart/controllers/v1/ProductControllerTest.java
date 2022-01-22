package com.shoppingcart.controllers.v1;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
	public void testFindById_whenSucess_expectStatusOK() throws Exception {
		String id = "61ebabb8a9f4da3258638b50";
		new ProductTemplate();
		Product product = ProductTemplate.getProductTemplate();
		
		when(productService.findById(id)).thenReturn(ResponseEntity.ok().body(product));
		
		mockMvc.perform(get(URI_PATH + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(".name").value("banana"))
                .andExpect(jsonPath(".price").value(10.0))
                .andExpect(jsonPath(".quantity").value(2))
                .andExpect(jsonPath(".isActive").value(true));
		
	}
	
//	@Test
//	public void testFindById_whenNotFound_expectStatus404() throws Exception {
//		String id = "61ebabb8a9f4da3258638b51";
//		
//		
//	}
}
