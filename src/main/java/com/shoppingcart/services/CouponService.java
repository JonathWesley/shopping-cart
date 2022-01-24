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

@Service
public class CouponService {

	@Autowired
	CouponRepository couponRepository;
	
	@Autowired
	MongoOperations mongoOperations;
	
	public ResponseEntity<Coupon> insertCoupon(Coupon coupon) {
		try {
			if(coupon.getCod() == null) 
				throw new InvalidInfoException("Cupom deve possuir um código.");
			if(coupon.getValue() == null) 
				throw new InvalidInfoException("Cupom deve possuir um valor.");
			if(coupon.getIsActive() == null) 
				throw new InvalidInfoException("Cupom deve possuir a informação de ativo.");
			
			Coupon couponInserted = couponRepository.save(coupon);

			return  couponInserted == null ? 
					ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null) :
					ResponseEntity.status(HttpStatus.CREATED).body(couponInserted);
			
		} catch (InvalidInfoException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	public ResponseEntity<Coupon> findById (String id) {
		Optional<Coupon> couponData = couponRepository.findById(id);
		
		return couponData.isPresent() ? 
				ResponseEntity.ok().body(couponData.get()) :
				ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		
	}
	
	public ResponseEntity<Coupon> updateCoupon(String id, Coupon coupon) {
		Optional<Coupon> couponData = couponRepository.findById(id);
		
		if (couponData.isPresent()) {
			try {
				if(coupon.getCod() == null) 
					throw new InvalidInfoException("Cupom deve possuir um código.");
				if(coupon.getValue() == null) 
					throw new InvalidInfoException("Cupom deve possuir um valor.");
				if(coupon.getIsActive() == null) 
					throw new InvalidInfoException("Cupom deve possuir a informação de ativo.");
				
				Coupon couponUpdated = couponData.get();
				couponUpdated.setCod(coupon.getCod());
				couponUpdated.setValue(coupon.getValue());
				couponUpdated.setIsActive(coupon.getIsActive());
			    return ResponseEntity.ok().body(couponRepository.save(couponUpdated));
			    
			} catch (InvalidInfoException e){
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
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
