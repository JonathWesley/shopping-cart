package entities;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Document(collection = "cart")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

	@Id
	private String id;
	
	@DBRef
	private Coupon coupon;
	
	@DBRef
	private List<ProductOnCart> products;
}
