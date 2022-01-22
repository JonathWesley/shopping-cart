package com.shoppingcart.controllers.v1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.entities.Product;
import com.shoppingcart.services.ProductService;

@RestController
@RequestMapping(path="/v1/product")
public class ProductController{
	@Autowired
	private ProductService productService;
	
	@PostMapping("/")
	public ResponseEntity<Product> insertProduct(@RequestBody Product product){
		return productService.insertProduct(product);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Product> getById(@PathVariable String id) {
		return productService.findById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Product> updateById(@PathVariable String id,
											  @RequestBody Product product) {
		return productService.updateProduct(id, product);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteById(@PathVariable String id) {
		return productService.deleteProduct(id);
	}
}
