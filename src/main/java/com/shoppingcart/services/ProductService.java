package com.shoppingcart.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shoppingcart.entities.Product;
import com.shoppingcart.exceptions.InvalidInfoException;
import com.shoppingcart.repositories.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	MongoOperations mongoOperations;
	
	public ResponseEntity<Product> insertProduct(Product product) {
		try {
			Product productInserted = productRepository.save(product);

			return  productInserted == null ? 
					ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null) :
					ResponseEntity.ok().body(productInserted);
		} catch (Exception e) {
			return null;
		}
	}
	
	public ResponseEntity<Product> findById (String id) {
		try {
			Optional<Product> productData = productRepository.findById(id);
			
			return productData.isPresent() ? 
					ResponseEntity.ok().body(productData.get()) :
					ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch(InvalidInfoException e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	public ResponseEntity<Product> updateProduct(String id, Product product) {
		Optional<Product> productData = productRepository.findById(id);
		
		if (productData.isPresent()) {
			Product productUpdated = productData.get();
			productUpdated.setName(product.getName());
			productUpdated.setPrice(product.getPrice());
			productUpdated.setQuantity(product.getQuantity());
			productUpdated.setIsActive(product.getIsActive());
		    return ResponseEntity.ok().body(productRepository.save(productUpdated));
		} else {
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	
	}
	
	public ResponseEntity<HttpStatus> deleteProduct(String id) {
		Optional<Product> productData = productRepository.findById(id);
		
		if (productData.isPresent()) {
			try {
				productRepository.deleteById(id);
			    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			} catch (Exception e) {
			    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
			
	}	
}
