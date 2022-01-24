package com.shoppingcart.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "coupon")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {
	
	@Id
	private String id;
	
	private String cod;
	
	private Double value;
	
	private Boolean isActive;
}
