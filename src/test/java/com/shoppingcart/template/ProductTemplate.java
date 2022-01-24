package com.shoppingcart.template;

import com.google.gson.Gson;
import com.shoppingcart.entities.Product;

public class ProductTemplate {

	public static Product getProductTemplate() {
		String mock = "{\"id\":\"61ebb454592cf360a063d4ba\",\"name\":\"banana\",\"price\":10.0,\"quantity\":2,\"isActive\":true}";
		Gson g = new Gson();
		return g.fromJson(mock, Product.class);
		
	}
	
	public static Product getProductTemplate2() {
		String mock = "{\"id\": \"61ec70b982025b4ebc44ba78\",\"name\": \"azeitona\",\"price\": 20.0,\"quantity\": 2,\"isActive\": true}";
		Gson g = new Gson();
		return g.fromJson(mock, Product.class);
		
	}
	
	public static Product getProductTemplate3() {
		String mock = "{\"id\":\"61ebb454592cf360a063d4ba\",\"name\":\"banana\",\"price\":7.0,\"quantity\":2,\"isActive\":true}";
		Gson g = new Gson();
		return g.fromJson(mock, Product.class);
		
	}
	
	public static Product insertProductTemplate() {
		String mock = "{\"name\":\"banana\",\"price\":10.0,\"quantity\":2,\"isActive\":true}";
		Gson g = new Gson();
		return g.fromJson(mock, Product.class);
		
	}
	
	public static Product insertProductBadRequestTemplate() {
		String mock = "{\"price\":10.0,\"quantity\":2,\"isActive\":true}";
		Gson g = new Gson();
		return g.fromJson(mock, Product.class);
		
	}
	
	public static Product updateProductTemplate() {
		String mock = "{\"name\":\"banana\",\"price\":12.0,\"quantity\":4,\"isActive\":true}";
		Gson g = new Gson();
		return g.fromJson(mock, Product.class);
		
	}
	
	public static Product getProductUpdatedTemplate() {
		String mock = "{\"id\":\"61ebb454592cf360a063d4ba\",\"name\":\"banana\",\"price\":12.0,\"quantity\":4,\"isActive\":true}";
		Gson g = new Gson();
		return g.fromJson(mock, Product.class);
		
	}
	
	public static Product updateProductBadRequestTemplate() {
		String mock = "{\"price\":12.0,\"quantity\":4,\"isActive\":true}";
		Gson g = new Gson();
		return g.fromJson(mock, Product.class);
		
	}
}