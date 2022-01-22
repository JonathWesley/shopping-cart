package com.shoppingcart.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shoppingcart.entities.Cart;
import com.shoppingcart.exceptions.InvalidInfoException;
import com.shoppingcart.repositories.CartRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CartService {

	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	MongoOperations mongoOperations;
	
	public ResponseEntity<Cart> findById (String id) {
		try {
			Optional<Cart> cartData = cartRepository.findById(id);
			
			return cartData.isPresent() ? 
					ResponseEntity.ok().body(cartData.get()) :
					ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch(InvalidInfoException e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	public ResponseEntity<HttpStatus> deleteCart(String id) {
		Optional<Cart> cartData = cartRepository.findById(id);
		
		if (cartData.isPresent()) {
			try {
				cartRepository.deleteById(id);
			    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			} catch (Exception e) {
			    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
			
	}	
}
