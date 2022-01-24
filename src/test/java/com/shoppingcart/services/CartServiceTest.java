package com.shoppingcart.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.shoppingcart.entities.Cart;
import com.shoppingcart.models.CartResponse;
import com.shoppingcart.repositories.CartRepository;
import com.shoppingcart.repositories.CouponRepository;
import com.shoppingcart.repositories.ProductRepository;
import com.shoppingcart.repositories.UserRepository;
import com.shoppingcart.template.CartTemplate;
import com.shoppingcart.template.CouponTemplate;
import com.shoppingcart.template.ProductTemplate;
import com.shoppingcart.template.UserTemplate;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {

	@InjectMocks
	CartService cartService;
	
	@Mock
	private CartRepository cartRepository;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	ProductRepository productRepository;
	
	@Mock
	CouponRepository couponRepository;
	
	@Test
	public void testAddProduct_whenSucessCartExists_statusOK() {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String idProduct = "61ebb454592cf360a063d4ba";
		Integer quantity = 1;
		
		Cart cart = CartTemplate.getCartTemplate2();

		when(userRepository.findById(idUser)).thenReturn(Optional.of(UserTemplate.getUserTemplate()));
		when(productRepository.findById(idProduct)).thenReturn(Optional.of(ProductTemplate.getProductTemplate3()));
		when(cartRepository.findByUser(idUser)).thenReturn(Optional.of(CartTemplate.getCartTemplate()));
		when(cartRepository.save(cart)).thenReturn(cart);
		
		ResponseEntity<Cart> result = cartService.addProduct(idUser, idProduct, quantity);
		
		assertEquals(cart, result.getBody());
	}
	
	@Test
	public void testAddProduct_whenSucessNewCart_statusOK() {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String idProduct = "61ec70b982025b4ebc44ba78";
		Integer quantity = 1;
		
		Cart cartInput = CartTemplate.getNewCartTemplate();
		Cart cartOutput = CartTemplate.getCartTemplate();

		when(userRepository.findById(idUser)).thenReturn(Optional.of(UserTemplate.getUserTemplate()));
		when(productRepository.findById(idProduct)).thenReturn(Optional.of(ProductTemplate.getProductTemplate2()));
		when(cartRepository.findByUser(idUser)).thenReturn(Optional.empty());
		when(cartRepository.save(cartInput)).thenReturn(cartOutput);
		
		ResponseEntity<Cart> result = cartService.addProduct(idUser, idProduct, quantity);
		
		assertEquals(cartOutput, result.getBody());
	}
	
	@Test
	public void testAddProduct_whenUserNotFound_statusNotFound() {
		String idUser = "111111111111111111111111";
		String idProduct = "61ec70b982025b4ebc44ba78";
		Integer quantity = 1;

		when(userRepository.findById(idUser)).thenReturn(Optional.empty());
		
		ResponseEntity result = cartService.addProduct(idUser, idProduct, quantity);
		
		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
		assertEquals("Usuário não encontrado.", result.getBody());
	}
	
	@Test
	public void testAddProduct_whenBadRequest_statusBadRequest() {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String idProduct = "61ec70b982025b4ebc44ba78";
		Integer quantity = 5;

		when(userRepository.findById(idUser)).thenReturn(Optional.of(UserTemplate.getUserTemplate()));
		when(productRepository.findById(idProduct)).thenReturn(Optional.of(ProductTemplate.getProductTemplate2()));
		
		ResponseEntity result = cartService.addProduct(idUser, idProduct, quantity);
		
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
		assertEquals("Produto não possui estoque.", result.getBody());
	}
	
	@Test
	public void testAddProduct_whenInternalServerError_statusInternalServerError() {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String idProduct = "61ec70b982025b4ebc44ba78";
		Integer quantity = 1;

		when(userRepository.findById(idUser)).thenReturn(null);
		
		ResponseEntity result = cartService.addProduct(idUser, idProduct, quantity);
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
	}
	
	@Test
	public void testRemoveProduct_whenSucess_statusOK() {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String idProduct = "61ebb454592cf360a063d4ba";
		
		Cart cartOutput = CartTemplate.getCartTemplate();

		when(cartRepository.findByUser(idUser)).thenReturn(Optional.of(CartTemplate.getCartTemplate2()));
		when(cartRepository.save(cartOutput)).thenReturn(cartOutput);
		
		ResponseEntity<Cart> result = cartService.removeProduct(idUser, idProduct);
		
		assertEquals(cartOutput, result.getBody());
	}
	
	@Test
	public void testRemoveProduct_whenSucessDeleteCart_statusNoContent() {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String idProduct = "61ec70b982025b4ebc44ba78";
		String idCart = "61ed83839195790cf274dd76";
		
		when(cartRepository.findByUser(idUser)).thenReturn(Optional.of(CartTemplate.getCartTemplate()));
		
		ResponseEntity<Cart> result = cartService.removeProduct(idUser, idProduct);
		
		assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
	}
	
	@Test
	public void testRemoveProduct_whenProductNotFound_statusNotFound() {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String idProduct = "111111111111111111111111";

		when(cartRepository.findByUser(idUser)).thenReturn(Optional.of(CartTemplate.getCartTemplate()));
		
		ResponseEntity result = cartService.removeProduct(idUser, idProduct);
		
		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
		assertEquals("Produto não encontrado.", result.getBody());
	}
	
	@Test
	public void testRemoveProduct_whenInternalServerError_statusInternalServerError() {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String idProduct = "61ec70b982025b4ebc44ba78";

		when(cartRepository.findByUser(idUser)).thenReturn(null);
		
		ResponseEntity result = cartService.removeProduct(idUser, idProduct);
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
	}
	
	@Test
	public void testUpdateProduct_whenSucess_statusOK() {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String idProduct = "61ec70b982025b4ebc44ba78";
		Integer quantity = 2;
		
		Cart cartOutput = CartTemplate.getCartUpdatedTemplate();

		when(cartRepository.findByUser(idUser)).thenReturn(Optional.of(CartTemplate.getCartTemplate()));
		when(cartRepository.save(cartOutput)).thenReturn(cartOutput);
		
		ResponseEntity<Cart> result = cartService.updateProduct(idUser, idProduct, quantity);
		
		assertEquals(cartOutput, result.getBody());
	}
	
	@Test
	public void testUpdateProduct_whenSucessDeleteCart_statusNoContent() {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String idProduct = "61ec70b982025b4ebc44ba78";
		Integer quantity = 0;
		
		when(cartRepository.findByUser(idUser)).thenReturn(Optional.of(CartTemplate.getCartTemplate()));
		
		ResponseEntity<Cart> result = cartService.updateProduct(idUser, idProduct, quantity);
		
		assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
	}
	
	@Test
	public void testUpdateProduct_whenCartNotFound_statusNotFound() {
		String idUser = "111111111111111111111111";
		String idProduct = "61ec70b982025b4ebc44ba78";
		Integer quantity = 1;

		when(cartRepository.findByUser(idUser)).thenReturn(Optional.empty());
		
		ResponseEntity result = cartService.updateProduct(idUser, idProduct, quantity);
		
		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
		assertEquals("Carrinho não encontrado.", result.getBody());
	}
	
	@Test
	public void testUpdateProduct_whenBadRequest_statusBadRequest() {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String idProduct = "61ec70b982025b4ebc44ba78";
		Integer quantity = 5;

		when(cartRepository.findByUser(idUser)).thenReturn(Optional.of(CartTemplate.getCartTemplate()));
		
		ResponseEntity result = cartService.updateProduct(idUser, idProduct, quantity);
		
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
		assertEquals("Produto não possui estoque.", result.getBody());
	}
	
	@Test
	public void testUpdateProduct_whenInternalServerError_statusInternalServerError() {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String idProduct = "61ec70b982025b4ebc44ba78";
		Integer quantity = 1;

		when(cartRepository.findByUser(idUser)).thenReturn(null);
		
		ResponseEntity result = cartService.updateProduct(idUser, idProduct, quantity);
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
	}
	
	@Test
	public void testAddCoupon_whenSucess_statusOK() {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String codCoupon = "free15";
		
		Cart cartOutput = CartTemplate.getCartCouponTemplate();

		when(cartRepository.findByUser(idUser)).thenReturn(Optional.of(CartTemplate.getCartTemplate()));
		when(couponRepository.findByCod(codCoupon)).thenReturn(Optional.of(CouponTemplate.getCouponTemplate()));
		when(cartRepository.save(cartOutput)).thenReturn(cartOutput);
		
		ResponseEntity<Cart> result = cartService.addCoupon(idUser, codCoupon);
		
		assertEquals(cartOutput, result.getBody());
	}
	
	@Test
	public void testAddCoupon_whenCouponNotFound_statusNotFound() {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String codCoupon = "xxx";

		when(cartRepository.findByUser(idUser)).thenReturn(Optional.of(CartTemplate.getCartTemplate()));
		when(couponRepository.findByCod(codCoupon)).thenReturn(Optional.empty());
		
		ResponseEntity result = cartService.addCoupon(idUser, codCoupon);
		
		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
		assertEquals("Cupom não encontrado.", result.getBody());
	}
	
	@Test
	public void testAddCoupon_whenBadRequest_statusBadRequest() {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String codCoupon = "free15";

		when(cartRepository.findByUser(idUser)).thenReturn(Optional.of(CartTemplate.getCartTemplate()));
		when(couponRepository.findByCod(codCoupon)).thenReturn(Optional.of(CouponTemplate.getCouponInactiveTemplate()));
		
		ResponseEntity result = cartService.addCoupon(idUser, codCoupon);
		
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
		assertEquals("Cupom não ativo.", result.getBody());
	}
	
	@Test
	public void testAddCoupon_whenInternalServerError_statusInternalServerError() {
		String idUser = "61ec6d5e3471474a3d0b5027";
		String codCoupon = "free15";

		when(cartRepository.findByUser(idUser)).thenReturn(null);
		
		ResponseEntity result = cartService.addCoupon(idUser, codCoupon);
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
	}
	
	@Test
	public void testRemoveCoupon_whenSucess_statusOK() {
		String idUser = "61ec6d5e3471474a3d0b5027";
		
		Cart cartOutput = CartTemplate.getCartTemplate();

		when(cartRepository.findByUser(idUser)).thenReturn(Optional.of(CartTemplate.getCartCouponTemplate()));
		when(cartRepository.save(cartOutput)).thenReturn(cartOutput);
		
		ResponseEntity<Cart> result = cartService.removeCoupon(idUser);
		
		assertEquals(cartOutput, result.getBody());
	}
	
	@Test
	public void testRemoveCoupon_whenCartNotFound_statusNotFound() {
		String idUser = "111111111111111111111111";

		when(cartRepository.findByUser(idUser)).thenReturn(Optional.empty());
		
		ResponseEntity result = cartService.removeCoupon(idUser);
		
		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
		assertEquals("Carrinho não encontrado.", result.getBody());
	}
	
	@Test
	public void testRemoveCoupon_whenInternalServerError_statusInternalServerError() {
		String idUser = "61ec6d5e3471474a3d0b5027";

		when(cartRepository.findByUser(idUser)).thenReturn(null);
		
		ResponseEntity result = cartService.removeCoupon(idUser);
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
	}
	
	@Test
	public void testFindByUserId_whenSucess_statusOK() {
		String idUser = "61ec6d5e3471474a3d0b5027";
		
		CartResponse cartOutput = CartTemplate.getCartResponseTemplate();

		when(cartRepository.findByUser(idUser)).thenReturn(Optional.of(CartTemplate.getCartTemplate()));
		
		ResponseEntity<Cart> result = cartService.findByUserId(idUser);
		
		assertEquals(cartOutput, result.getBody());
	}
	
	@Test
	public void testFindByUserId_whenCartNotFound_statusNotFound() {
		String idUser = "111111111111111111111111";

		when(cartRepository.findByUser(idUser)).thenReturn(Optional.empty());
		
		ResponseEntity result = cartService.findByUserId(idUser);
		
		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
		assertEquals("Carrinho não encontrado.", result.getBody());
	}
	
	@Test
	public void testFindByUserId_whenInternalServerError_statusInternalServerError() {
		String idUser = "61ec6d5e3471474a3d0b5027";

		when(cartRepository.findByUser(idUser)).thenReturn(null);
		
		ResponseEntity result = cartService.findByUserId(idUser);
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
	}
	
	@Test
	public void testDeleteByUserId_whenSucess_statusNoContent() {
		String idUser = "61ec6d5e3471474a3d0b5027";
		
		when(cartRepository.findByUser(idUser)).thenReturn(Optional.of(CartTemplate.getCartTemplate()));
		
		ResponseEntity<Cart> result = cartService.deleteCartByUserId(idUser);
		
		assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
	}
	
	@Test
	public void testDeleteByUserId_whenCartNotFound_statusNotFound() {
		String idUser = "111111111111111111111111";

		when(cartRepository.findByUser(idUser)).thenReturn(Optional.empty());
		
		ResponseEntity result = cartService.deleteCartByUserId(idUser);
		
		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
		assertEquals("Carrinho não encontrado.", result.getBody());
	}
	
	@Test
	public void testDeleteByUserId_whenInternalServerError_statusInternalServerError() {
		String idUser = "61ec6d5e3471474a3d0b5027";

		when(cartRepository.findByUser(idUser)).thenReturn(null);
		
		ResponseEntity result = cartService.deleteCartByUserId(idUser);
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
	}
	
	@Test
	public void testFinalizeCart_whenSucess_statusNoContent() {
		String idUser = "61ec6d5e3471474a3d0b5027";
		
		when(cartRepository.findByUser(idUser)).thenReturn(Optional.of(CartTemplate.getCartTemplate()));
		
		ResponseEntity<Cart> result = cartService.finalizePurchase(idUser);
		
		assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
	}
	
	@Test
	public void testFinalizeCart_whenCartNotFound_statusNotFound() {
		String idUser = "111111111111111111111111";

		when(cartRepository.findByUser(idUser)).thenReturn(Optional.empty());
		
		ResponseEntity result = cartService.finalizePurchase(idUser);
		
		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
		assertEquals("Carrinho não encontrado.", result.getBody());
	}
	
	@Test
	public void testFinalizeCart_whenBadRequest_statusBadRequest() {
		String idUser = "61ec6d5e3471474a3d0b5027";

		when(cartRepository.findByUser(idUser)).thenReturn(Optional.of(CartTemplate.getCartFinalizeBadTemplate()));
		
		ResponseEntity result = cartService.finalizePurchase(idUser);
		
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
		assertEquals("Produto não possui estoque.", result.getBody());
	}
	
	@Test
	public void testFinalizeCart_whenInternalServerError_statusInternalServerError() {
		String idUser = "61ec6d5e3471474a3d0b5027";

		when(cartRepository.findByUser(idUser)).thenReturn(null);
		
		ResponseEntity result = cartService.finalizePurchase(idUser);
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
	}
}
