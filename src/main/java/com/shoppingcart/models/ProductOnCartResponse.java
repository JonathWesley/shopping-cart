package com.shoppingcart.models;

import com.shoppingcart.entities.Product;
import com.shoppingcart.entities.ProductOnCart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductOnCartResponse {
	
	private Product product;
	private Integer quantity;
	private Double subTotalValue;
	
	public ProductOnCartResponse(Product product, Integer quantity) {
		this.product = product;
		this.quantity = quantity;
		this.subTotalValue = product.getPrice() * quantity;
	}
	
	public void updateSubTotalValue() {
		this.subTotalValue = this.product.getPrice() * this.quantity;
	}
	
	public static ProductOnCartResponse convertToDto(ProductOnCart productOnCart) {
		ProductOnCartResponse productOnCartResponse = ProductOnCartResponse.builder().product(productOnCart.getProduct()).quantity(productOnCart.getQuantity()).build();
		//productOnCartResponse.updateSubTotalValue();
	    return productOnCartResponse;
	}
}
