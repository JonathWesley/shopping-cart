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

import com.shoppingcart.entities.Coupon;
import com.shoppingcart.services.CouponService;

@RestController
@RequestMapping(path="/v1/coupon")
public class CouponController{
	@Autowired
	private CouponService couponService;
	
	@PostMapping("/")
	public ResponseEntity<Coupon> insertCoupon(@RequestBody Coupon coupon){
		return couponService.insertCoupon(coupon);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Coupon> getById(@PathVariable String id) {
		return couponService.findById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Coupon> updateById(@PathVariable String id,
											 @RequestBody Coupon coupon) {
		return couponService.updateCoupon(id, coupon);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteById(@PathVariable String id) {
		return couponService.deleteCoupon(id);
	}
}
