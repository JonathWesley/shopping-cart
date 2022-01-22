package com.shoppingcart.template;

import com.google.gson.Gson;
import com.shoppingcart.entities.Product;

public class ProductTemplate {

	public static Product getProductTemplate() {
		String mock = "{\"id\":\"61ebb454592cf360a063d4ba\",\"name\":\"banana\",\"price\":10.0,\"quantity\":2,\"isActive\":true}";
		Gson g = new Gson();
		return g.fromJson(mock, Product.class);
		
	}
}