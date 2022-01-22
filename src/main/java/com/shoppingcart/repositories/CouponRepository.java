package com.shoppingcart.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.shoppingcart.entities.Coupon;

@Repository
public interface CouponRepository extends MongoRepository<Coupon, String>{

}
