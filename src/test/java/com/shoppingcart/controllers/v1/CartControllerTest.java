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
import com.shoppingcart.entities.Cart;
import com.shoppingcart.models.CartResponse;
import com.shoppingcart.services.CartService;
import com.shoppingcart.template.CartTemplate;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, 
		classes = ShoppingCartApplication.class
		)
public class CartControllerTest {

	@InjectMocks
	CartController cartController;
	
	@Mock
	CartService cartService;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
	}
	
	private static final String URI_PATH = "/v1/cart/";
	
	@Test
	public void testAddProduct_whenSucess_expectStatusOK() throws Exception {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String idProduct = "61ec70b982025b4ebc44ba78";
		Integer quantity = 1;
	
		Cart cartOutput = CartTemplate.getCartTemplate();
		
		when(cartService.addProduct(idUser, idProduct, quantity)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(cartOutput));
		
		mockMvc.perform(post(URI_PATH + "product/" + idUser + "/" + idProduct + "/" + String.valueOf(quantity)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("61ed83839195790cf274dd76"))
                .andExpect(jsonPath(".user.id").value("61ec6d5e3471474a3d0b5027"))
                .andExpect(jsonPath(".user.name").value("Jonath"))
                .andExpect(jsonPath(".products[0].product.id").value("61ec70b982025b4ebc44ba78"))
                .andExpect(jsonPath(".products[0].product.name").value("azeitona"))
                .andExpect(jsonPath(".products[0].product.price").value(20.0))
                .andExpect(jsonPath(".products[0].product.quantity").value(2))
                .andExpect(jsonPath(".products[0].product.isActive").value(true))
                .andExpect(jsonPath(".products[0].quantity").value(1));
	
	}
	
	@Test
	public void testAddProduct_whenNotFound_expectStatusNotFound() throws Exception {
		String idUser = "111111111111111111111111";
		String idProduct = "61ec70b982025b4ebc44ba78";
		Integer quantity = 1;
			
		when(cartService.addProduct(idUser, idProduct, quantity)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
		
		mockMvc.perform(post(URI_PATH + "product/" + idUser + "/" + idProduct + "/" + String.valueOf(quantity)))
                .andDo(print())
                .andExpect(status().isNotFound());
	
	}
	
	@Test
	public void testAddProduct_whenBadRequest_expectStatusBadRequest() throws Exception {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String idProduct = "61ec70b982025b4ebc44ba78";
		Integer quantity = 999;
			
		when(cartService.addProduct(idUser, idProduct, quantity)).thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
		
		mockMvc.perform(post(URI_PATH + "product/" + idUser + "/" + idProduct + "/" + String.valueOf(quantity)))
                .andDo(print())
                .andExpect(status().isBadRequest());
	}
	
	@Test
	public void testAddProduct_whenInternalServerError_expectStatusInternalServerError() throws Exception {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String idProduct = "61ec70b982025b4ebc44ba78";
		Integer quantity = 1;
			
		when(cartService.addProduct(idUser, idProduct, quantity)).thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
		
		mockMvc.perform(post(URI_PATH + "product/" + idUser + "/" + idProduct + "/" + String.valueOf(quantity)))
                .andDo(print())
                .andExpect(status().isInternalServerError());
	
	}
	
	@Test
	public void testRemoveProduct_whenSucess_expectStatusOK() throws Exception {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String idProduct = "61ebb454592cf360a063d4ba";
	
		Cart cartOutput = CartTemplate.getCartTemplate();
		
		when(cartService.removeProduct(idUser, idProduct)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(cartOutput));
		
		mockMvc.perform(delete(URI_PATH + "product/" + idUser + "/" + idProduct))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("61ed83839195790cf274dd76"))
                .andExpect(jsonPath(".user.id").value("61ec6d5e3471474a3d0b5027"))
                .andExpect(jsonPath(".user.name").value("Jonath"))
                .andExpect(jsonPath(".products[0].product.id").value("61ec70b982025b4ebc44ba78"))
                .andExpect(jsonPath(".products[0].product.name").value("azeitona"))
                .andExpect(jsonPath(".products[0].product.price").value(20.0))
                .andExpect(jsonPath(".products[0].product.quantity").value(2))
                .andExpect(jsonPath(".products[0].product.isActive").value(true))
                .andExpect(jsonPath(".products[0].quantity").value(1));
	
	}
	
	@Test
	public void testRemoveProduct_whenNoContent_expectStatusNoContent() throws Exception {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String idProduct = "61ec70b982025b4ebc44ba78";
			
		when(cartService.removeProduct(idUser, idProduct)).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).body(null));
		
		mockMvc.perform(delete(URI_PATH + "product/" + idUser + "/" + idProduct))
                .andDo(print())
                .andExpect(status().isNoContent());
	}
	
	@Test
	public void testRemoveProduct_whenNotFound_expectStatusNotFound() throws Exception {
		String idUser = "111111111111111111111111";
		String idProduct = "61ebb454592cf360a063d4ba";
			
		when(cartService.removeProduct(idUser, idProduct)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
		
		mockMvc.perform(delete(URI_PATH + "product/" + idUser + "/" + idProduct))
                .andDo(print())
                .andExpect(status().isNotFound());
	
	}
	
	@Test
	public void testRemoveProduct_whenInternalServerError_expectStatusInternalServerError() throws Exception {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String idProduct = "61ebb454592cf360a063d4ba";
			
		when(cartService.removeProduct(idUser, idProduct)).thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
		
		mockMvc.perform(delete(URI_PATH + "product/" + idUser + "/" + idProduct))
                .andDo(print())
                .andExpect(status().isInternalServerError());
	
	}
	
	@Test
	public void testUpdateProduct_whenSucess_expectStatusOK() throws Exception {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String idProduct = "61ec70b982025b4ebc44ba78";
		Integer quantity = 2;
	
		Cart cartOutput = CartTemplate.getCartUpdatedTemplate();
		
		when(cartService.updateProduct(idUser, idProduct, quantity)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(cartOutput));
		
		mockMvc.perform(put(URI_PATH + "product/" + idUser + "/" + idProduct + "/" + String.valueOf(quantity)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("61ed83839195790cf274dd76"))
                .andExpect(jsonPath(".user.id").value("61ec6d5e3471474a3d0b5027"))
                .andExpect(jsonPath(".user.name").value("Jonath"))
                .andExpect(jsonPath(".products[0].product.id").value("61ec70b982025b4ebc44ba78"))
                .andExpect(jsonPath(".products[0].product.name").value("azeitona"))
                .andExpect(jsonPath(".products[0].product.price").value(20.0))
                .andExpect(jsonPath(".products[0].product.quantity").value(2))
                .andExpect(jsonPath(".products[0].product.isActive").value(true))
                .andExpect(jsonPath(".products[0].quantity").value(2));
	
	}
	
	@Test
	public void testUpdateProduct_whenSucess_expectStatusNoContent() throws Exception {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String idProduct = "61ec70b982025b4ebc44ba78";
		Integer quantity = 0;
			
		when(cartService.updateProduct(idUser, idProduct, quantity)).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).body(null));
		
		mockMvc.perform(put(URI_PATH + "product/" + idUser + "/" + idProduct + "/" + String.valueOf(quantity)))
                .andDo(print())
                .andExpect(status().isNoContent());
	}
	
	@Test
	public void testUpdateProduct_whenBadRequest_expectStatusBadRequest() throws Exception {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String idProduct = "61ec70b982025b4ebc44ba78";
		Integer quantity = 999;
		
		when(cartService.updateProduct(idUser, idProduct, quantity)).thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
		
		mockMvc.perform(put(URI_PATH + "product/" + idUser + "/" + idProduct + "/" + String.valueOf(quantity)))
                .andDo(print())
                .andExpect(status().isBadRequest());
	}
	
	@Test
	public void testUpdateProduct_whenNotFound_expectStatusNotFound() throws Exception {
		String idUser = "111111111111111111111111";
		String idProduct = "61ebb454592cf360a063d4ba";
		Integer quantity = 2;
		
		when(cartService.updateProduct(idUser, idProduct, quantity)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
		
		mockMvc.perform(put(URI_PATH + "product/" + idUser + "/" + idProduct + "/" + String.valueOf(quantity)))
                .andDo(print())
                .andExpect(status().isNotFound());
	
	}
	
	@Test
	public void testUpdateProduct_whenInternalServerError_expectStatusInternalServerError() throws Exception {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String idProduct = "61ebb454592cf360a063d4ba";
		Integer quantity = 2;
		
		when(cartService.updateProduct(idUser, idProduct, quantity)).thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
		
		mockMvc.perform(put(URI_PATH + "product/" + idUser + "/" + idProduct + "/" + String.valueOf(quantity)))
                .andDo(print())
                .andExpect(status().isInternalServerError());
	
	}
	
	@Test
	public void testAddCoupon_whenSucess_expectStatusOK() throws Exception {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String idCoupon = "61ebbcb6bfd53e6b7517b8a4";
	
		Cart cartOutput = CartTemplate.getCartCouponTemplate();
		
		when(cartService.addCoupon(idUser, idCoupon)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(cartOutput));
		
		mockMvc.perform(post(URI_PATH + "coupon/" + idUser + "/" + idCoupon))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("61ed83839195790cf274dd76"))
                .andExpect(jsonPath(".user.id").value("61ec6d5e3471474a3d0b5027"))
                .andExpect(jsonPath(".user.name").value("Jonath"))
                .andExpect(jsonPath(".coupon.id").value("61ebbcb6bfd53e6b7517b8a4"))
                .andExpect(jsonPath(".coupon.cod").value("free15"))
                .andExpect(jsonPath(".coupon.value").value(15.0))
                .andExpect(jsonPath(".coupon.isActive").value(true))
                .andExpect(jsonPath(".products[0].product.id").value("61ec70b982025b4ebc44ba78"))
                .andExpect(jsonPath(".products[0].product.name").value("azeitona"))
                .andExpect(jsonPath(".products[0].product.price").value(20.0))
                .andExpect(jsonPath(".products[0].product.quantity").value(2))
                .andExpect(jsonPath(".products[0].product.isActive").value(true))
                .andExpect(jsonPath(".products[0].quantity").value(1));
	
	}
	
	@Test
	public void testAddCoupon_whenBadRequest_expectStatusBadRequest() throws Exception {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String idCoupon = "61ebbcb6bfd53e6b7517b8a4";
			
		when(cartService.addCoupon(idUser, idCoupon)).thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
		
		mockMvc.perform(post(URI_PATH + "coupon/" + idUser + "/" + idCoupon))
                .andDo(print())
                .andExpect(status().isBadRequest());
	}
	
	@Test
	public void testAddCoupon_whenNotFound_expectStatusNotFound() throws Exception {
		String idUser = "111111111111111111111111";
		String idCoupon = "61ebbcb6bfd53e6b7517b8a4";
				
		when(cartService.addCoupon(idUser, idCoupon)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
		
		mockMvc.perform(post(URI_PATH + "coupon/" + idUser + "/" + idCoupon))
                .andDo(print())
                .andExpect(status().isNotFound());
	
	}
	
	@Test
	public void testAddCoupon_whenInternalServerError_expectStatusInternalServerError() throws Exception {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String idCoupon = "61ebbcb6bfd53e6b7517b8a4";
				
		when(cartService.addCoupon(idUser, idCoupon)).thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
		
		mockMvc.perform(post(URI_PATH + "coupon/" + idUser + "/" + idCoupon))
                .andDo(print())
                .andExpect(status().isInternalServerError());
	
	}
	
	@Test
	public void testRemoveCoupon_whenSucess_expectStatusOK() throws Exception {
		String idUser = "61ec6d5e3471474a3d0b5027";
	
		Cart cartOutput = CartTemplate.getCartTemplate();
		
		when(cartService.removeCoupon(idUser)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(cartOutput));
		
		mockMvc.perform(delete(URI_PATH + "coupon/" + idUser))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("61ed83839195790cf274dd76"))
                .andExpect(jsonPath(".user.id").value("61ec6d5e3471474a3d0b5027"))
                .andExpect(jsonPath(".user.name").value("Jonath"))
                .andExpect(jsonPath(".products[0].product.id").value("61ec70b982025b4ebc44ba78"))
                .andExpect(jsonPath(".products[0].product.name").value("azeitona"))
                .andExpect(jsonPath(".products[0].product.price").value(20.0))
                .andExpect(jsonPath(".products[0].product.quantity").value(2))
                .andExpect(jsonPath(".products[0].product.isActive").value(true))
                .andExpect(jsonPath(".products[0].quantity").value(1));
	
	}
	
	@Test
	public void testRemoveCoupon_whenNotFound_expectStatusNotFound() throws Exception {
		String idUser = "111111111111111111111111";
		
		when(cartService.removeCoupon(idUser)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
		
		mockMvc.perform(delete(URI_PATH + "coupon/" + idUser))
                .andDo(print())
                .andExpect(status().isNotFound());
	
	}
	
	@Test
	public void testRemoveCoupon_whenInternalServerError_expectStatusInternalServerError() throws Exception {
		String idUser = "61ec6d5e3471474a3d0b5027";

		when(cartService.removeCoupon(idUser)).thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
		
		mockMvc.perform(delete(URI_PATH + "coupon/" + idUser))
                .andDo(print())
                .andExpect(status().isInternalServerError());
	
	}
	
	@Test
	public void testFindByUserId_whenSucess_expectStatusOK() throws Exception {
		String idUser = "61ec6d5e3471474a3d0b5027";
	
		CartResponse cartOutput = CartTemplate.getCartResponseTemplate();
		
		when(cartService.findByUserId(idUser)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(cartOutput));
		
		mockMvc.perform(get(URI_PATH + "user/" + idUser))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("61ed83839195790cf274dd76"))
                .andExpect(jsonPath(".user.id").value("61ec6d5e3471474a3d0b5027"))
                .andExpect(jsonPath(".user.name").value("Jonath"))
                .andExpect(jsonPath(".products[0].product.id").value("61ec70b982025b4ebc44ba78"))
                .andExpect(jsonPath(".products[0].product.name").value("azeitona"))
                .andExpect(jsonPath(".products[0].product.price").value(20.0))
                .andExpect(jsonPath(".products[0].product.quantity").value(2))
                .andExpect(jsonPath(".products[0].product.isActive").value(true))
                .andExpect(jsonPath(".products[0].quantity").value(1))
                .andExpect(jsonPath(".products[0].subTotalValue").value(20.0))
                .andExpect(jsonPath(".totalValue").value(20.0));
	
	}
	
	@Test
	public void testFindByUserId_whenNotFound_expectStatusNotFound() throws Exception {
		String idUser = "111111111111111111111111";
		
		when(cartService.findByUserId(idUser)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
		
		mockMvc.perform(get(URI_PATH + "user/" + idUser))
                .andDo(print())
                .andExpect(status().isNotFound());
	
	}
	
	@Test
	public void testFindByUserId_whenInternalServerError_expectStatusInternalServerError() throws Exception {
		String idUser = "61ec6d5e3471474a3d0b5027";

		when(cartService.findByUserId(idUser)).thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
		
		mockMvc.perform(get(URI_PATH + "user/" + idUser))
                .andDo(print())
                .andExpect(status().isInternalServerError());
	
	}
	
	@Test
	public void testDeleteCartByUserId_whenSucess_expectStatusNoContent() throws Exception {
		String idUser = "61ec6d5e3471474a3d0b5027";
			
		when(cartService.deleteCartByUserId(idUser)).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).body(null));
		
		mockMvc.perform(delete(URI_PATH + "user/" + idUser))
                .andDo(print())
                .andExpect(status().isNoContent());
	
	}
	
	@Test
	public void testDeleteByUserId_whenNotFound_expectStatusNotFound() throws Exception {
		String idUser = "111111111111111111111111";
		
		when(cartService.deleteCartByUserId(idUser)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
		
		mockMvc.perform(delete(URI_PATH + "user/" + idUser))
                .andDo(print())
                .andExpect(status().isNotFound());
	
	}
	
	@Test
	public void testDeleteByUserId_whenInternalServerError_expectStatusInternalServerError() throws Exception {
		String idUser = "61ec6d5e3471474a3d0b5027";

		when(cartService.deleteCartByUserId(idUser)).thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
		
		mockMvc.perform(delete(URI_PATH + "user/" + idUser))
                .andDo(print())
                .andExpect(status().isInternalServerError());
	
	}
	
	@Test
	public void testFinalizePurchase_whenSucess_expectStatusNoContent() throws Exception {
		String idUser = "61ec6d5e3471474a3d0b5027";
			
		when(cartService.finalizePurchase(idUser)).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).body(null));
		
		mockMvc.perform(delete(URI_PATH + "finalize/" + idUser))
                .andDo(print())
                .andExpect(status().isNoContent());
	
	}
	
	@Test
	public void testFinalizePurchase_whenNotFound_expectStatusNotFound() throws Exception {
		String idUser = "111111111111111111111111";
		
		when(cartService.finalizePurchase(idUser)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
		
		mockMvc.perform(delete(URI_PATH + "finalize/" + idUser))
                .andDo(print())
                .andExpect(status().isNotFound());
	
	}
	
	@Test
	public void testFinalizePurchase_whenBadRequest_expectStatusBadRequest() throws Exception {
		String idUser = "x";
		
		when(cartService.finalizePurchase(idUser)).thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
		
		mockMvc.perform(delete(URI_PATH + "finalize/" + idUser))
                .andDo(print())
                .andExpect(status().isBadRequest());
	
	}
	
	@Test
	public void testFinalizePurchase_whenInternalServerError_expectStatusInternalServerError() throws Exception {
		String idUser = "61ec6d5e3471474a3d0b5027";

		when(cartService.finalizePurchase(idUser)).thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
		
		mockMvc.perform(delete(URI_PATH + "finalize/" + idUser))
                .andDo(print())
                .andExpect(status().isInternalServerError());
	
	}
}
