package com.shoppingcart.controllers.v1;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
	public void testInsert_whenSucess_expectStatusCreated() throws Exception {
		String body = "{\"cod\":\"free15\",\"value\":15.0,\"isActive\":true}";
	
		new CouponTemplate();
		Coupon couponInput = CouponTemplate.insertCouponTemplate();
		Coupon couponOutput = CouponTemplate.getCouponTemplate();
		
		when(couponService.insertCoupon(couponInput)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(couponOutput));
		
		mockMvc.perform(post(URI_PATH).content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(".id").value("61ebbcb6bfd53e6b7517b8a4"))
                .andExpect(jsonPath(".cod").value("free15"))
                .andExpect(jsonPath(".value").value(15.0))
                .andExpect(jsonPath(".isActive").value(true));
	
	}
	
	@Test
	public void testInsert_whenInternalServerError_expectStatusInternalServerError() throws Exception {
		String body = "{\"cod\":\"free15\",\"value\":15.0,\"isActive\":true}";
	
		new CouponTemplate();
		Coupon couponInput = CouponTemplate.insertCouponTemplate();
		
		when(couponService.insertCoupon(couponInput)).thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
		
		mockMvc.perform(post(URI_PATH).content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
	
	}
	
	@Test
	public void testInsert_whenBadRequest_expectStatusBadRequest() throws Exception {
		String body = "{\"value\":15.0,\"isActive\":true}";
	
		new CouponTemplate();
		Coupon couponInput = CouponTemplate.insertCouponBadRequestTemplate();
		
		when(couponService.insertCoupon(couponInput)).thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
		
		mockMvc.perform(post(URI_PATH).content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
	
	}
	
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
	
	@Test
	public void testUpdate_whenSucess_expectStatusOK() throws Exception {
		String id = "61ebbcb6bfd53e6b7517b8a4";
		String body = "{\"cod\":\"free10\",\"value\":10.0,\"isActive\":true}";
	
		new CouponTemplate();
		Coupon couponInput = CouponTemplate.updateCouponTemplate();
		Coupon couponOutput = CouponTemplate.getCouponUpdatedTemplate();
		
		when(couponService.updateCoupon(id, couponInput)).thenReturn(ResponseEntity.ok().body(couponOutput));
		
		mockMvc.perform(put(URI_PATH + id).content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(".id").value("61ebbcb6bfd53e6b7517b8a4"))
                .andExpect(jsonPath(".cod").value("free10"))
                .andExpect(jsonPath(".value").value(10.0))
                .andExpect(jsonPath(".isActive").value(true));
	
	}
	
	@Test
	public void testUpdate_whenInternalServerError_expectStatusInternalServerError() throws Exception {
		String id = "61ebbcb6bfd53e6b7517b8a4";
		String body = "{\"cod\":\"free10\",\"value\":10.0,\"isActive\":true}";
	
		new CouponTemplate();
		Coupon couponInput = CouponTemplate.updateCouponTemplate();
		
		when(couponService.updateCoupon(id, couponInput)).thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
		
		mockMvc.perform(put(URI_PATH + id).content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
	
	}
	
	@Test
	public void testUpdate_whenBadRequest_expectStatusBadRequest() throws Exception {
		String id = "61ebbcb6bfd53e6b7517b8a4";
		String body = "{\"value\":10.0,\"isActive\":true}";
	
		new CouponTemplate();
		Coupon couponInput = CouponTemplate.updateCouponBadRequestTemplate();
		
		when(couponService.updateCoupon(id, couponInput)).thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
		
		mockMvc.perform(put(URI_PATH + id).content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
	
	}
	
	@Test
	public void testUpdate_whenNotFound_expectStatusNotFound() throws Exception {
		String id = "111111111111111111111111";
		String body = "{\"cod\":\"free10\",\"value\":10.0,\"isActive\":true}";
	
		new CouponTemplate();
		Coupon couponInput = CouponTemplate.updateCouponTemplate();
		
		when(couponService.updateCoupon(id, couponInput)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
		
		mockMvc.perform(put(URI_PATH + id).content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
	
	}
	
	@Test
	public void testDalete_whenSucess_expectStatusNoContent() throws Exception {
		String id = "61ebbcb6bfd53e6b7517b8a4";
		
		when(couponService.deleteCoupon(id)).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).body(null));
		
		mockMvc.perform(delete(URI_PATH + id))
                .andDo(print())
                .andExpect(status().isNoContent());
	
	}
	
	@Test
	public void testDelete_whenInternalServerError_expectStatusInternalServerError() throws Exception {
		String id = "61ebbcb6bfd53e6b7517b8a4";
		
		when(couponService.deleteCoupon(id)).thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
		
		mockMvc.perform(delete(URI_PATH + id))
                .andDo(print())
                .andExpect(status().isInternalServerError());
	
	}
	
	@Test
	public void testDelete_whenNotFound_expectStatusNotFound() throws Exception {
		String id = "111111111111111111111111";
		
		when(couponService.deleteCoupon(id)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
		
		mockMvc.perform(delete(URI_PATH + id))
                .andDo(print())
                .andExpect(status().isNotFound());
	
	}
}
