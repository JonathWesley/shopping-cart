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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.shoppingcart.ShoppingCartApplication;
import com.shoppingcart.entities.Coupon;
import com.shoppingcart.services.CouponService;
import com.shoppingcart.template.CouponTemplate;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, 
		classes = ShoppingCartApplication.class
		)
public class CouponControllerTest {

	@InjectMocks
	CouponController couponController;
	
	@Mock
	CouponService couponService;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(couponController).build();
	}
	
	private static final String URI_PATH = "/v1/coupon/";
	
	@Test
	public void testFindById_whenSucess_expectStatusOK() throws Exception {
		String id = "61ebabb8a9f4da3258638b50";
		new CouponTemplate();
		Coupon coupon = CouponTemplate.getCouponTemplate();
		
		when(couponService.findById(id)).thenReturn(ResponseEntity.ok().body(coupon));
		
		mockMvc.perform(get(URI_PATH + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(".cod").value("free15"))
                .andExpect(jsonPath(".value").value(15.0))
                .andExpect(jsonPath(".isActive").value(true));
		
	}
	
	@Test
	public void testFindById_whenNotFound_expectStatusNotFound() throws Exception {
		String id = "61ebabb8a9f4da3258638b51";
		
		when(couponService.findById(id)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
		
		mockMvc.perform(get(URI_PATH + id))
		        .andDo(print())
		        .andExpect(status().isNotFound());
	}
}
