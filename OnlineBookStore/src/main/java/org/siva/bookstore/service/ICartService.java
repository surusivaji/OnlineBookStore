package org.siva.bookstore.service;

import java.util.List;

import org.siva.bookstore.models.Cart;
import org.siva.bookstore.models.User;

public interface ICartService {
	
	Boolean addToCart(int uid, int bid);
	
	List<Cart> getCartsByUser(User user);
	
	Long countCartByUser(User user);

	Boolean updateQuantity(String status, int cid);
	
}
