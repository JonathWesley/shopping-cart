package com.shoppingcart.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shoppingcart.entities.User;
import com.shoppingcart.exceptions.InvalidInfoException;
import com.shoppingcart.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	MongoOperations mongoOperations;
	
	public ResponseEntity<User> insertUser(User user) {
		try {
			if(user.getName() == null) 
				throw new InvalidInfoException("Produto deve possuir um nome.");
			
			User userInserted = userRepository.save(user);

			return  userInserted == null ? 
					ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null) :
					ResponseEntity.status(HttpStatus.CREATED).body(userInserted);
		} catch (InvalidInfoException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	public ResponseEntity<User> findById(String id) {
		Optional<User> userData = userRepository.findById(id);
			
		return userData.isPresent() ? 
				ResponseEntity.ok().body(userData.get()) :
				ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	public ResponseEntity<User> updateUser(String id, User user) {
		Optional<User> userData = userRepository.findById(id);
		
		if (userData.isPresent()) {
			try {
				if(user.getName() == null) 
					throw new InvalidInfoException("Produto deve possuir um nome.");
				
				User userUpdated = userData.get();
				userUpdated.setName(user.getName());
			    return ResponseEntity.ok().body(userRepository.save(userUpdated));
			} catch (InvalidInfoException e){
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
		} else {
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	
	}
	
	public ResponseEntity<HttpStatus> deleteUser(String id) {
		Optional<User> userData = userRepository.findById(id);
		
		if (userData.isPresent()) {
			try {
				userRepository.deleteById(id);
			    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			} catch (Exception e) {
			    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
			
	}	
}
