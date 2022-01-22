package com.shoppingcart.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shoppingcart.entities.Cart;
import com.shoppingcart.entities.Product;
import com.shoppingcart.entities.User;
import com.shoppingcart.exceptions.InvalidInfoException;
import com.shoppingcart.models.ProductOnCart;
import com.shoppingcart.repositories.CartRepository;
import com.shoppingcart.repositories.ProductRepository;
import com.shoppingcart.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CartService {

	@Autowired
	CartRepository cartRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	MongoOperations mongoOperations;
	
	public ResponseEntity<Cart> addProduct(String idUser, String idProduct, Integer quantity) {
		try {
			Optional<User> userData = userRepository.findById(idUser);
			
			if(userData.isPresent()) {
				Optional<Product> productData = productRepository.findById(idProduct);
				
				if(productData.isPresent()) {
					if(productData.get().getQuantity() < quantity) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
					}
					
					Optional<Cart> cartData = cartRepository.findByUser(idUser);
					
					if(cartData.isPresent()) {
						Cart cartUpdated = cartData.get();
						
						List<ProductOnCart> products = cartUpdated.getProducts();
						products.add(new ProductOnCart(productData.get(), quantity));
						
						cartUpdated.setProducts(products);
						
					    return ResponseEntity.ok().body(cartRepository.save(cartUpdated));
					}else {
						ProductOnCart productOnCart = ProductOnCart.builder().product(productData.get()).quantity(quantity).build();
						
						List<ProductOnCart> products = List.of(productOnCart);
						
						Cart cartCreated = Cart.builder().user(userData.get()).products(products).build();
						
						return ResponseEntity.ok().body(cartRepository.save(cartCreated));
					}
					
				}
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	public ResponseEntity<Cart> removeProduct(String idUser, String idProduct) {
		try {
			Optional<Cart> cartData = cartRepository.findByUser(idUser);
			
			if(cartData.isPresent()) {
				Cart cartUpdated = cartData.get();
				
				List<ProductOnCart> products = cartUpdated.getProducts();
				
				ProductOnCart productOnCart = products.stream().filter(product -> product.getProduct().getId().equals(idProduct)).findFirst().get();
				
				if(productOnCart != null) {
					products.remove(productOnCart);
					
					if(products.isEmpty()) {
						cartRepository.deleteById(cartUpdated.getId());
						
					    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
					}else {
						cartUpdated.setProducts(products);
						
					    return ResponseEntity.ok().body(cartRepository.save(cartUpdated));
					}
				}
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	public ResponseEntity<Cart> updateProduct(String idUser, String idProduct, Integer quantity) {
		try {
			Optional<Cart> cartData = cartRepository.findByUser(idUser);
			
			if(cartData.isPresent()) {
				Cart cartUpdated = cartData.get();
				
				List<ProductOnCart> products = cartUpdated.getProducts();
				
				ProductOnCart productOnCart = products.stream().filter(product -> product.getProduct().getId().equals(idProduct)).findFirst().get();
				
				if(productOnCart != null) {
					if(productOnCart.getProduct().getQuantity() < quantity) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
					}
					
					products.remove(productOnCart);
					
					if(quantity > 0) {
						productOnCart.setQuantity(quantity);
						products.add(productOnCart);
					}
					
					if(products.isEmpty()) {
						cartRepository.deleteById(cartUpdated.getId());
						
					    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
					}else {
						cartUpdated.setProducts(products);
						
					    return ResponseEntity.ok().body(cartRepository.save(cartUpdated));
					}
				}
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
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
