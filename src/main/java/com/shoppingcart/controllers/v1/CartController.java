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

import com.shoppingcart.services.CartService;

@RestController
@RequestMapping(path="/v1/cart")
public class CartController{
	@Autowired
	private CartService cartService;
	
	@PostMapping("/product/{idUser}/{idProduct}/{quantity}")
	public ResponseEntity addProduct(@PathVariable String idUser, @PathVariable String idProduct, @PathVariable Integer quantity) {
		return cartService.addProduct(idUser, idProduct, quantity);
	}
	
	@DeleteMapping("/product/{idUser}/{idProduct}")
	public ResponseEntity removeProduct(@PathVariable String idUser, @PathVariable String idProduct) {
		return cartService.removeProduct(idUser, idProduct);
	}
	
	@PutMapping("/product/{idUser}/{idProduct}/{quantity}")
	public ResponseEntity updateProduct(@PathVariable String idUser, @PathVariable String idProduct, @PathVariable Integer quantity) {
		return cartService.updateProduct(idUser, idProduct, quantity);
	}
	
	@PostMapping("/coupon/{idUser}/{codCoupon}")
	public ResponseEntity addCoupon(@PathVariable String idUser, @PathVariable String codCoupon) {
		return cartService.addCoupon(idUser, codCoupon);
	}
	
	@DeleteMapping("/coupon/{idUser}")
	public ResponseEntity removeCoupon(@PathVariable String idUser) {
		return cartService.removeCoupon(idUser);
	}
	
	@GetMapping("/user/{idUser}")
	public ResponseEntity getByUserId(@PathVariable String idUser) {
		return cartService.findByUserId(idUser);
	}
	
	@DeleteMapping("/user/{idUser}")
	public ResponseEntity deleteByUserId(@PathVariable String idUser) {
		return cartService.deleteCartByUserId(idUser);
	}
	
	@DeleteMapping("/finalize/{idUser}")
	public ResponseEntity finalizePurchase(@PathVariable String idUser) {
		return cartService.finalizePurchase(idUser);
	}
}
