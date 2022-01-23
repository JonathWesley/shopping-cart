package com.shoppingcart.models;

import java.util.ArrayList;
import java.util.List;

import com.shoppingcart.entities.Cart;
import com.shoppingcart.entities.Coupon;
import com.shoppingcart.entities.Cart;
import com.shoppingcart.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
	
	private String id;
	private User user;
	private Coupon coupon;
	private List<ProductOnCartResponse> products;
	private Double totalValue;
	
	public void updateTotalValue() {
		products.forEach((item) -> {
			item.updateSubTotalValue();
		});
		
		this.totalValue = this.products.stream().mapToDouble(o -> o.getSubTotalValue()).sum();
		
		if(this.coupon != null) {
			this.totalValue = this.totalValue - this.coupon.getValue() < 0 ? 0.0 : this.totalValue - this.coupon.getValue();
		}
	}
	
	public static CartResponse convertToDto(Cart cart) {
		List<ProductOnCartResponse> productsResponse = new ArrayList<ProductOnCartResponse>();
		cart.getProducts().forEach((item) -> {
			productsResponse.add(ProductOnCartResponse.convertToDto(item));
		});
		
		CartResponse cartResponse = CartResponse.builder().id(cart.getId()).user(cart.getUser()).coupon(cart.getCoupon()).products(productsResponse).build();
		cartResponse.updateTotalValue();
	    return cartResponse;
	}
}
