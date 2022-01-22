package com.shoppingcart.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.shoppingcart.entities.Cart;

@Repository
public interface CartRepository extends MongoRepository<Cart, String>{

}
