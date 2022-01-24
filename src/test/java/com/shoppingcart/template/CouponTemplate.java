package com.shoppingcart.template;

import com.google.gson.Gson;
import com.shoppingcart.entities.Coupon;

public class CouponTemplate {

	public static Coupon getCouponTemplate() {
		String mock = "{\"id\":\"61ebbcb6bfd53e6b7517b8a4\",\"cod\":\"free15\",\"value\":15.0,\"isActive\":true}";
		Gson g = new Gson();
		return g.fromJson(mock, Coupon.class);
		
	}
	
	public static Coupon getCouponInactiveTemplate() {
		String mock = "{\"id\":\"61ebbcb6bfd53e6b7517b8a4\",\"cod\":\"free15\",\"value\":15.0,\"isActive\":false}";
		Gson g = new Gson();
		return g.fromJson(mock, Coupon.class);
		
	}
	
	public static Coupon insertCouponTemplate() {
		String mock = "{\"cod\":\"free15\",\"value\":15.0,\"isActive\":true}";
		Gson g = new Gson();
		return g.fromJson(mock, Coupon.class);
		
	}
	
	public static Coupon insertCouponBadRequestTemplate() {
		String mock = "{\"value\":15.0,\"isActive\":true}";
		Gson g = new Gson();
		return g.fromJson(mock, Coupon.class);
		
	}
	
	public static Coupon updateCouponTemplate() {
		String mock = "{\"cod\":\"free10\",\"value\":10.0,\"isActive\":true}";
		Gson g = new Gson();
		return g.fromJson(mock, Coupon.class);
		
	}
	
	public static Coupon getCouponUpdatedTemplate() {
		String mock = "{\"id\":\"61ebbcb6bfd53e6b7517b8a4\",\"cod\":\"free10\",\"value\":10.0,\"isActive\":true}";
		Gson g = new Gson();
		return g.fromJson(mock, Coupon.class);
		
	}
	
	public static Coupon updateCouponBadRequestTemplate() {
		String mock = "{\"value\":10.0,\"isActive\":true}";
		Gson g = new Gson();
		return g.fromJson(mock, Coupon.class);
		
	}
}

