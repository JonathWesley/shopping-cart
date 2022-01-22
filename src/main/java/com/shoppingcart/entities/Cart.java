package com.shoppingcart.entities;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.shoppingcart.models.ProductOnCart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "cart")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

	@Id
	private String id;
	
	@DBRef
	private User user;
	
	@DBRef
	private Coupon coupon;
	
	private List<ProductOnCart> products;
	
}
