package com.shoppingcart.entities;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "product_on_cart")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductOnCart {
	
	@DBRef
	private Product product;
	
	private Integer quantity;
}
