package com.shoppingcart.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.shoppingcart.entities.Cart;
import com.shoppingcart.entities.Coupon;
import com.shoppingcart.entities.Product;
import com.shoppingcart.entities.ProductOnCart;
import com.shoppingcart.entities.User;
import com.shoppingcart.exceptions.InvalidInfoException;
import com.shoppingcart.exceptions.ObjectNotFoundException;
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
	
	private final Gson gson = new Gson();
	
	public ResponseEntity addProduct(String idUser, String idProduct, Integer quantity) {
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
						if(productOnCart != null) 
							throw new InvalidInfoException("Produto já existe no carrinho.");
						
						products.add(new ProductOnCart(productData.get(), quantity));
						
						cartUpdated.setProducts(products);
						
						log.info("Carrinho a ser criado: ", gson.toJson(cartUpdated));
					    return ResponseEntity.ok().body(cartRepository.save(cartUpdated));
					}else {
						ProductOnCart productOnCart = ProductOnCart.builder().product(productData.get()).quantity(quantity).build();
						
						List<ProductOnCart> products = List.of(productOnCart);
						
						Cart cartCreated = Cart.builder().user(userData.get()).products(products).build();
						
						log.info("Carrinho a ser criado: ", gson.toJson(cartCreated));
						return ResponseEntity.ok().body(cartRepository.save(cartCreated));
					}
					
				} else 
					throw new ObjectNotFoundException("Produto não encontrado.");
				
			}else 			
				throw new ObjectNotFoundException("Usuário não encontrado.");
			
		} catch (ObjectNotFoundException e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}  catch (InvalidInfoException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	public ResponseEntity removeProduct(String idUser, String idProduct) {
		try {
			Optional<Cart> cartData = cartRepository.findByUser(idUser);
			
			if(cartData.isPresent()) {
				Cart cartUpdated = cartData.get();
				
				List<ProductOnCart> products = cartUpdated.getProducts();
				
				ProductOnCart productOnCart = products.stream().filter(product -> product.getProduct().getId().equals(idProduct)).findFirst().orElse(null);
				
				if(productOnCart != null) {
					log.info("Produto a ser removido: ", gson.toJson(productOnCart));
					products.remove(productOnCart);
					
					if(products.isEmpty()) {
						log.info("Carrinho a ser removido: ", gson.toJson(cartUpdated));
						cartRepository.deleteById(cartUpdated.getId());
						
					    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Produto removido.");
					}else {
						cartUpdated.setProducts(products);
						
						log.info("Carrinho atualizado (produto removido): ", gson.toJson(cartUpdated));
					    return ResponseEntity.ok().body(cartRepository.save(cartUpdated));
					}
				} else 
					throw new ObjectNotFoundException("Produto não encontrado.");			
			} else 
				throw new ObjectNotFoundException("Carrinho não encontrado.");
		} catch (ObjectNotFoundException e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	public ResponseEntity updateProduct(String idUser, String idProduct, Integer quantity) {
		try {
			Optional<Cart> cartData = cartRepository.findByUser(idUser);
			
			if(cartData.isPresent()) {
				Cart cartUpdated = cartData.get();
				
				List<ProductOnCart> products = cartUpdated.getProducts();
				
				ProductOnCart productOnCart = products.stream().filter(product -> product.getProduct().getId().equals(idProduct)).findFirst().orElse(null);
				
				if(productOnCart != null) {
					log.info("Produto a ser atualizado no carrinho: ", gson.toJson(productOnCart));
					if(productOnCart.getProduct().getQuantity() < quantity) {
						throw new InvalidInfoException("Produto não possui estoque.");
					}
					
					products.remove(productOnCart);
					
					if(quantity > 0) {
						productOnCart.setQuantity(quantity);
						products.add(productOnCart);
					}
					
					if(products.isEmpty()) {
						log.info("Carrinho a ser removido: ", gson.toJson(cartUpdated));
						cartRepository.deleteById(cartUpdated.getId());
						
					    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nenhum produto no carrinho, carrinho removido.");
					}else {
						cartUpdated.setProducts(products);
						
						log.info("Carrinho atualizado (produto atualizado): ", gson.toJson(cartUpdated));
					    return ResponseEntity.ok().body(cartRepository.save(cartUpdated));
					}
				} else 
					throw new ObjectNotFoundException("Produto não encontrado.");
			} else 
				throw new ObjectNotFoundException("Carrinho não encontrado.");
		} catch (ObjectNotFoundException e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}  catch (InvalidInfoException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	public ResponseEntity addCoupon(String idUser, String codCoupon) {
		try {
			Optional<Cart> cartData = cartRepository.findByUser(idUser);
			
			if(cartData.isPresent()) {												
				Optional<Coupon> couponData = couponRepository.findByCod(codCoupon);
				
				if(couponData.isPresent()) {
					log.info("Cupom a ser utilizado: ", gson.toJson(couponData.get()));
					if(!couponData.get().getIsActive()) 
						throw new InvalidInfoException("Cupom não ativo.");
					
					Cart cartUpdated = cartData.get();
					
					cartUpdated.setCoupon(couponData.get());
					
					log.info("Carrinho atualizado (cupom adicionado): ", gson.toJson(cartUpdated));
				    return ResponseEntity.ok().body(cartRepository.save(cartUpdated));
				} else 
					throw new ObjectNotFoundException("Cupom não encontrado.");
			} else 
				throw new ObjectNotFoundException("Carrinho não encontrado.");
		} catch (ObjectNotFoundException e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}  catch (InvalidInfoException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	public ResponseEntity removeCoupon(String idUser) {
		try {
			Optional<Cart> cartData = cartRepository.findByUser(idUser);
			
			if(cartData.isPresent()) {												
				Cart cartUpdated = cartData.get();
					
				cartUpdated.setCoupon(null);
				
				log.info("Carrinho atualizado (cupom removido): ", gson.toJson(cartUpdated));
				return ResponseEntity.ok().body(cartRepository.save(cartUpdated));
			} else 
				throw new ObjectNotFoundException("Carrinho não encontrado.");
		} catch (ObjectNotFoundException e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}  catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	public ResponseEntity findByUserId(String idUser) {
		try {
			Optional<Cart> cartData = cartRepository.findByUser(idUser);
			
			if(cartData.isPresent()) {
				CartResponse cartResponse = CartResponse.convertToDto(cartData.get());
				
				log.info("Carrinho encontrado: ", gson.toJson(cartResponse));
				return ResponseEntity.ok().body(cartResponse);
			} else 
				throw new ObjectNotFoundException("Carrinho não encontrado.");
		} catch (ObjectNotFoundException e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	public ResponseEntity deleteCartByUserId(String idUser) {
		try {
			Optional<Cart> cartData = cartRepository.findByUser(idUser);
			
			if (cartData.isPresent()) {
				log.info("Carrinho a ser removido: ", gson.toJson(cartData.get()));
				cartRepository.deleteByUser(idUser);
			    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Carrinho removido.");
				
			} else 
				throw new ObjectNotFoundException("Carrinho não encontrado.");
		} catch (ObjectNotFoundException e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
		    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	public ResponseEntity finalizePurchase(String idUser) {
		try {
			Optional<Cart> cartData = cartRepository.findByUser(idUser);
		
			if (cartData.isPresent()) {
					Cart cart = cartData.get();
					log.info("Carrinho a ser finalizado: ", gson.toJson(cart));
					
					cart.getProducts().forEach((item) -> {					
						if(!item.getProduct().getIsActive()) 
							throw new InvalidInfoException("Produto não ativo.");
						if(item.getProduct().getQuantity() < item.getQuantity()) 
							throw new InvalidInfoException("Produto não possui estoque.");
						
						item.getProduct().setQuantity(item.getProduct().getQuantity() - item.getQuantity());
						productRepository.save(item.getProduct());
					});
					
					cartRepository.deleteByUser(idUser);
				    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Compra finalizada, carrinho removido.");
				
			} else 
				throw new ObjectNotFoundException("Carrinho não encontrado.");
		} catch (ObjectNotFoundException e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}  catch (InvalidInfoException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}catch (Exception e) {
		    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
