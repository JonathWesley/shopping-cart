package com.shoppingcart.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shoppingcart.entities.Coupon;
import com.shoppingcart.exceptions.InvalidInfoException;
import com.shoppingcart.repositories.CouponRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CouponService {

	@Autowired
	CouponRepository couponRepository;
	
	@Autowired
	MongoOperations mongoOperations;
	
	public ResponseEntity<Coupon> insertCoupon(Coupon coupon) {
		try {
			Coupon couponInserted = couponRepository.save(coupon);

			return  couponInserted == null ? 
					ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null) :
					ResponseEntity.ok().body(couponInserted);
		} catch (Exception e) {
			return null;
		}
	}
	
	public ResponseEntity<Coupon> findById (String id) {
		try {
			Optional<Coupon> couponData = couponRepository.findById(id);
			
			return couponData.isPresent() ? 
					ResponseEntity.ok().body(couponData.get()) :
					ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch(InvalidInfoException e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	public ResponseEntity<Coupon> updateCoupon(String id, Coupon coupon) {
		Optional<Coupon> couponData = couponRepository.findById(id);
		
		if (couponData.isPresent()) {
			Coupon couponUpdated = couponData.get();
			couponUpdated.setCod(coupon.getCod());
			couponUpdated.setValue(coupon.getValue());
			couponUpdated.setIsActive(coupon.getIsActive());
		    return ResponseEntity.ok().body(couponRepository.save(couponUpdated));
		} else {
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	
	}
	
	public ResponseEntity<HttpStatus> deleteCoupon(String id) {
		Optional<Coupon> couponData = couponRepository.findById(id);
		
		if (couponData.isPresent()) {
			try {
				couponRepository.deleteById(id);
			    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			} catch (Exception e) {
			    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
	}	
}
