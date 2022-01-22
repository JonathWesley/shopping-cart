package com.shoppingcart.controllers.v1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.entities.User;
import com.shoppingcart.services.UserService;

@RestController
@RequestMapping(path="/v1/user")
public class UserController{
	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<User> insertUser(@RequestBody User user){
		return userService.insertUser(user);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable String id) {
		return userService.findById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> updateById(@PathVariable String id,
											  @RequestBody User user) {
		return userService.updateUser(id, user);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteById(@PathVariable String id) {
		return userService.deleteUser(id);
	}
}
