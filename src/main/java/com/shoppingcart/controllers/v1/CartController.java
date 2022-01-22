package com.shoppingcart.controllers.v1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.entities.Cart;
import com.shoppingcart.services.CartService;

@RestController
@RequestMapping(path="/v1/cart")
public class CartController{
	@Autowired
	private CartService cartService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Cart> getById(@PathVariable String id) {
		return cartService.findById(id);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteById(@PathVariable String id) {
		return cartService.deleteCart(id);
	}
}
