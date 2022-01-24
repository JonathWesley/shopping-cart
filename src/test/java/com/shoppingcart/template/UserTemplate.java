package com.shoppingcart.template;

import com.google.gson.Gson;
import com.shoppingcart.entities.User;

public class UserTemplate {

	public static User getUserTemplate() {
		String mock = "{\"id\":\"61ec6d5e3471474a3d0b5027\",\"name\":\"Jonath\"}";
		Gson g = new Gson();
		return g.fromJson(mock, User.class);
		
	}
	
	public static User insertUserTemplate() {
		String mock = "{\"name\":\"Jonath\"}";
		Gson g = new Gson();
		return g.fromJson(mock, User.class);
		
	}
	
	public static User userBadRequestTemplate() {
		String mock = "{}";
		Gson g = new Gson();
		return g.fromJson(mock, User.class);
		
	}
	
	public static User updateUserTemplate() {
		String mock = "{\"name\":\"Bruno\"}";
		Gson g = new Gson();
		return g.fromJson(mock, User.class);
		
	}
	
	public static User getUserUpdatedTemplate() {
		String mock = "{\"id\":\"61ec6d5e3471474a3d0b5027\",\"name\":\"Bruno\"}";
		Gson g = new Gson();
		return g.fromJson(mock, User.class);
		
	}
}