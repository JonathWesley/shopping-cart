package com.shoppingcart.template;

import com.google.gson.Gson;
import com.shoppingcart.entities.Cart;
import com.shoppingcart.models.CartResponse;

public class CartTemplate {

	public static Cart getNewCartTemplate() {
		String mock = "{\"user\": {\"id\": \"61ec6d5e3471474a3d0b5027\",\"name\": \"Jonath\"},\"coupon\": null,\"products\": [{\"product\": {\"id\": \"61ec70b982025b4ebc44ba78\",\"name\": \"azeitona\",\"price\": 20.0,\"quantity\": 2,\"isActive\": true},\"quantity\": 1}]}";
		Gson g = new Gson();
		return g.fromJson(mock, Cart.class);
	}
	
	public static Cart getCartTemplate() {
		String mock = "{\"id\": \"61ed83839195790cf274dd76\",\"user\": {\"id\": \"61ec6d5e3471474a3d0b5027\",\"name\": \"Jonath\"},\"coupon\": null,\"products\": [{\"product\": {\"id\": \"61ec70b982025b4ebc44ba78\",\"name\": \"azeitona\",\"price\": 20.0,\"quantity\": 2,\"isActive\": true},\"quantity\": 1}]}";
		Gson g = new Gson();
		return g.fromJson(mock, Cart.class);
	}
	
	public static Cart getCartTemplate2() {
		String mock = "{\"id\": \"61ed83839195790cf274dd76\",\"user\": {\"id\": \"61ec6d5e3471474a3d0b5027\",\"name\": \"Jonath\"},\"coupon\": null,\"products\": [{\"product\": {\"id\": \"61ec70b982025b4ebc44ba78\",\"name\": \"azeitona\",\"price\": 20.0,\"quantity\": 2,\"isActive\": true},\"quantity\": 1},{\"product\": {\"id\": \"61ebb454592cf360a063d4ba\",\"name\": \"banana\",\"price\": 7.0,\"quantity\": 2,\"isActive\": true},\"quantity\": 1}]}";
		Gson g = new Gson();
		return g.fromJson(mock, Cart.class);
	}

	public static Cart getCartUpdatedTemplate() {
		String mock = "{\"id\": \"61ed83839195790cf274dd76\",\"user\": {\"id\": \"61ec6d5e3471474a3d0b5027\",\"name\": \"Jonath\"},\"coupon\": null,\"products\": [{\"product\": {\"id\": \"61ec70b982025b4ebc44ba78\",\"name\": \"azeitona\",\"price\": 20.0,\"quantity\": 2,\"isActive\": true},\"quantity\": 2}]}";
		Gson g = new Gson();
		return g.fromJson(mock, Cart.class);
	}

	public static Cart getCartCouponTemplate() {
		String mock = "{\"id\": \"61ed83839195790cf274dd76\",\"user\": {\"id\": \"61ec6d5e3471474a3d0b5027\",\"name\": \"Jonath\"},\"coupon\": {\"id\": \"61ebbcb6bfd53e6b7517b8a4\",\"cod\": \"free15\",\"value\": 15.0,\"isActive\": true},\"products\": [{\"product\": {\"id\": \"61ec70b982025b4ebc44ba78\",\"name\": \"azeitona\",\"price\": 20.0,\"quantity\": 2,\"isActive\": true},\"quantity\": 1}]}";
		Gson g = new Gson();
		return g.fromJson(mock, Cart.class);
	}

	public static CartResponse getCartResponseTemplate() {
		String mock = "{\"id\": \"61ed83839195790cf274dd76\",\"user\": {\"id\": \"61ec6d5e3471474a3d0b5027\",\"name\": \"Jonath\"},\"coupon\": null,\"products\": [{\"product\": {\"id\": \"61ec70b982025b4ebc44ba78\",\"name\": \"azeitona\",\"price\": 20.0,\"quantity\": 2,\"isActive\": true},\"quantity\": 1,\"subTotalValue\": 20.0}],\"totalValue\": 20.0}";
		Gson g = new Gson();
		return g.fromJson(mock, CartResponse.class);
	}
	
	public static Cart getCartFinalizeBadTemplate() {
		String mock = "{\"id\": \"61ed83839195790cf274dd76\",\"user\": {\"id\": \"61ec6d5e3471474a3d0b5027\",\"name\": \"Jonath\"},\"coupon\": null,\"products\": [{\"product\": {\"id\": \"61ec70b982025b4ebc44ba78\",\"name\": \"azeitona\",\"price\": 20.0,\"quantity\": 0,\"isActive\": true},\"quantity\": 1}]}";
		Gson g = new Gson();
		return g.fromJson(mock, Cart.class);
	}
}