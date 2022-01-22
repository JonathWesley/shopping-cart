package com.shoppingcart.template;

import com.google.gson.Gson;
import com.shoppingcart.entities.Coupon;

public class CouponTemplate {

	public static Coupon getCouponTemplate() {
		String mock = "{\"id\":\"61ebbcb6bfd53e6b7517b8a4\",\"cod\":\"free15\",\"value\":15.0,\"isActive\":true}";
		Gson g = new Gson();
		return g.fromJson(mock, Coupon.class);
		
	}
}

