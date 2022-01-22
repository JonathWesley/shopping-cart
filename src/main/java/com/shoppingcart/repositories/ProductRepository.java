package com.shoppingcart.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.shoppingcart.entities.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>{

}
