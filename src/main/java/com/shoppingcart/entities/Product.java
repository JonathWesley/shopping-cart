package com.shoppingcart.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Document(collection = "product")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	
	@Id
	private String id;
	
	private String name;
	
	private Double price;
	
	private Integer quantity;
	
	private Boolean isActive;
}
