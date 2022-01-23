package com.shoppingcart.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shoppingcart.entities.Cart;
import com.shoppingcart.entities.Coupon;
import com.shoppingcart.entities.Product;
import com.shoppingcart.entities.ProductOnCart;
import com.shoppingcart.entities.User;
import com.shoppingcart.exceptions.InvalidInfoException;
import com.shoppingcart.models.CartResponse;
import com.shoppingcart.repositories.CartRepository;
import com.shoppingcart.repositories.CouponRepository;
import com.shoppingcart.repositories.ProductRepository;
import com.shoppingcart.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CartService {

	@Autowired
	CartRepository cartRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	CouponRepository couponRepository;
	
	@Autowired
	MongoOperations mongoOperations;
	
	public ResponseEntity<Cart> addProduct(String idUser, String idProduct, Integer quantity) {
		try {
			Optional<User> userData = userRepository.findById(idUser);
			
			if(userData.isPresent()) {
				Optional<Product> productData = productRepository.findById(idProduct);
				
				if(productData.isPresent()) {
					if(!productData.get().getIsActive()) 
						throw new InvalidInfoException("Produto não ativo.");
					if(productData.get().getQuantity() < quantity) 
						throw new InvalidInfoException("Produto não possui estoque.");
					
				
					Optional<Cart> cartData = cartRepository.findByUser(idUser);
					
					if(cartData.isPresent()) {
						Cart cartUpdated = cartData.get();
						
						List<ProductOnCart> products = cartUpdated.getProducts();
						
						ProductOnCart productOnCart = products.stream().filter(product -> product.getProduct().getId().equals(idProduct)).findFirst().orElse(null);
						if(productOnCart != null) {
							throw new InvalidInfoException("Produto já existe no carrinho.");
						}
						
						products.add(new ProductOnCart(productData.get(), quantity));
						
						cartUpdated.setProducts(products);
						
					    return ResponseEntity.ok().body(cartRepository.save(cartUpdated));
					}else {
						ProductOnCart productOnCart = ProductOnCart.builder().product(productData.get()).quantity(quantity).build();
						
						List<ProductOnCart> products = List.of(productOnCart);
						
						Cart cartCreated = Cart.builder().user(userData.get()).products(products).build();
						
						return ResponseEntity.ok().body(cartRepository.save(cartCreated));
					}
					
				}
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (InvalidInfoException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	public ResponseEntity<Cart> removeProduct(String idUser, String idProduct) {
		try {
			Optional<Cart> cartData = cartRepository.findByUser(idUser);
			
			if(cartData.isPresent()) {
				Cart cartUpdated = cartData.get();
				
				List<ProductOnCart> products = cartUpdated.getProducts();
				
				ProductOnCart productOnCart = products.stream().filter(product -> product.getProduct().getId().equals(idProduct)).findFirst().orElse(null);
				
				if(productOnCart != null) {
					products.remove(productOnCart);
					
					if(products.isEmpty()) {
						cartRepository.deleteById(cartUpdated.getId());
						
					    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
					}else {
						cartUpdated.setProducts(products);
						
					    return ResponseEntity.ok().body(cartRepository.save(cartUpdated));
					}
				}				
			}
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	public ResponseEntity<Cart> updateProduct(String idUser, String idProduct, Integer quantity) {
		try {
			Optional<Cart> cartData = cartRepository.findByUser(idUser);
			
			if(cartData.isPresent()) {
				Cart cartUpdated = cartData.get();
				
				List<ProductOnCart> products = cartUpdated.getProducts();
				
				ProductOnCart productOnCart = products.stream().filter(product -> product.getProduct().getId().equals(idProduct)).findFirst().orElse(null);
				
				if(productOnCart != null) {
					if(productOnCart.getProduct().getQuantity() < quantity) {
						throw new InvalidInfoException("Produto não possui estoque.");
					}
					
					products.remove(productOnCart);
					
					if(quantity > 0) {
						productOnCart.setQuantity(quantity);
						products.add(productOnCart);
					}
					
					if(products.isEmpty()) {
						cartRepository.deleteById(cartUpdated.getId());
						
					    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
					}else {
						cartUpdated.setProducts(products);
						
					    return ResponseEntity.ok().body(cartRepository.save(cartUpdated));
					}
				}
			}
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (InvalidInfoException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	public ResponseEntity<Cart> addCoupon(String idUser, String codCoupon) {
		try {
			Optional<Cart> cartData = cartRepository.findByUser(idUser);
			
			if(cartData.isPresent()) {												
				Optional<Coupon> couponData = couponRepository.findByCod(codCoupon);
				
				if(couponData.isPresent()) {
					if(!couponData.get().getIsActive()) 
						throw new InvalidInfoException("Cupon não ativo.");
					
					Cart cartUpdated = cartData.get();
					
					cartUpdated.setCoupon(couponData.get());
					
				    return ResponseEntity.ok().body(cartRepository.save(cartUpdated));
				}
			}
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (InvalidInfoException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	public ResponseEntity<Cart> removeCoupon(String idUser) {
		try {
			Optional<Cart> cartData = cartRepository.findByUser(idUser);
			
			if(cartData.isPresent()) {												
				Cart cartUpdated = cartData.get();
					
				cartUpdated.setCoupon(null);
					
				return ResponseEntity.ok().body(cartRepository.save(cartUpdated));
			}
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	public ResponseEntity<CartResponse> findByUserId(String idUser) {
		try {
			Optional<Cart> cartData = cartRepository.findByUser(idUser);
			
			if(cartData.isPresent()) {
				CartResponse cartResponse = CartResponse.convertToDto(cartData.get());
				
				return ResponseEntity.ok().body(cartResponse);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch(InvalidInfoException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public ResponseEntity<Cart> deleteCartByUserId(String idUser) {
		Optional<Cart> cartData = cartRepository.findByUser(idUser);
		
		if (cartData.isPresent()) {
			try {
				cartRepository.deleteByUser(idUser);
			    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			} catch (Exception e) {
			    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	public ResponseEntity<Cart> finalizePurchase(String idUser) {
		Optional<Cart> cartData = cartRepository.findByUser(idUser);
		
		if (cartData.isPresent()) {
			try {
				Cart cart = cartData.get();
				cart.getProducts().forEach((item) -> {					
					if(!item.getProduct().getIsActive()) 
						throw new InvalidInfoException("Produto não ativo.");
					if(item.getProduct().getQuantity() < item.getQuantity()) 
						throw new InvalidInfoException("Produto não possui estoque.");
					
					item.getProduct().setQuantity(item.getProduct().getQuantity() - item.getQuantity());
					productRepository.save(item.getProduct());
				});
				
				cartRepository.deleteByUser(idUser);
			    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			} catch (InvalidInfoException e){
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}catch (Exception e) {
			    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
}
