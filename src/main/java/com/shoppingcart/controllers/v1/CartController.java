package com.shoppingcart.controllers.v1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.entities.Cart;
import com.shoppingcart.models.CartResponse;
import com.shoppingcart.services.CartService;

@RestController
@RequestMapping(path="/v1/cart")
public class CartController{
	@Autowired
	private CartService cartService;
	
	@PostMapping("/product/{idUser}/{idProduct}/{quantity}")
	public ResponseEntity<Cart> addProduct(@PathVariable String idUser, @PathVariable String idProduct, @PathVariable Integer quantity) {
		return cartService.addProduct(idUser, idProduct, quantity);
	}
	
	@DeleteMapping("/product/{idUser}/{idProduct}")
	public ResponseEntity<Cart> removeProduct(@PathVariable String idUser, @PathVariable String idProduct) {
		return cartService.removeProduct(idUser, idProduct);
	}
	
	@PutMapping("/product/{idUser}/{idProduct}/{quantity}")
	public ResponseEntity<Cart> updateProduct(@PathVariable String idUser, @PathVariable String idProduct, @PathVariable Integer quantity) {
		return cartService.updateProduct(idUser, idProduct, quantity);
	}
	
	@PostMapping("/coupon/{idUser}/{codCoupon}")
	public ResponseEntity<Cart> addCoupon(@PathVariable String idUser, @PathVariable String codCoupon) {
		return cartService.addCoupon(idUser, codCoupon);
	}
	
	@DeleteMapping("/coupon/{idUser}")
	public ResponseEntity<Cart> removeCoupon(@PathVariable String idUser) {
		return cartService.removeCoupon(idUser);
	}
	
	@GetMapping("/user/{idUser}")
	public ResponseEntity<CartResponse> getByUserId(@PathVariable String idUser) {
		return cartService.findByUserId(idUser);
	}
	
	@DeleteMapping("/user/{idUser}")
	public ResponseEntity<Cart> deleteByUserId(@PathVariable String idUser) {
		return cartService.deleteCartByUserId(idUser);
	}
	
	@DeleteMapping("/finalize/{idUser}")
	public ResponseEntity<Cart> finalizePurchase(@PathVariable String idUser) {
		return cartService.finalizePurchase(idUser);
	}
}
