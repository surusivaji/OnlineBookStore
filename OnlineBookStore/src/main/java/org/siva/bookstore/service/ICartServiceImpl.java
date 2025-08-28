package org.siva.bookstore.service;

import java.util.ArrayList;
import java.util.List;

import org.siva.bookstore.models.Book;
import org.siva.bookstore.models.Cart;
import org.siva.bookstore.models.User;
import org.siva.bookstore.repository.BookRepository;
import org.siva.bookstore.repository.CartRepository;
import org.siva.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ICartServiceImpl implements ICartService {
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Override
	public Boolean addToCart(int uid, int bid) {
		try {
			Book book = bookRepository.findById(bid).get();
			User user = userRepository.findById(uid).get();
			Cart cartInfo = cartRepository.findByBookAndUser(book, user);
			Cart cart = null;
			if (cartInfo==null) {
				cart = new Cart();
				cart.setBook(book);
				cart.setUser(user);
				cart.setQuantity(1);
			}
			else {
				cart = cartInfo;
				cart.setQuantity(cart.getQuantity()+1);
			}
			Cart save = cartRepository.save(cart);
			if (save!=null) {
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public List<Cart> getCartsByUser(User user) {
		try {
			List<Cart> carts = cartRepository.findByUser(user);
			List<Cart> cartInfo = new ArrayList<Cart>();
			double totalOrderPrice = 0.0;
			for (Cart cart : carts) {
				double totalPrice = cart.getQuantity()*cart.getBook().getPrice();
				cart.setTotalPrice(totalPrice);
				totalOrderPrice = totalOrderPrice+totalPrice;
				cart.setTotalOrderPrice(totalOrderPrice);
				cartInfo.add(cart);
			}
			return cartInfo;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Long countCartByUser(User user) {
		try {
			Long totalCarts = cartRepository.countCartByUser(user);
			return totalCarts;
		}
		catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Boolean updateQuantity(String status, int cid) {
		try {
			Cart cart = cartRepository.findById(cid).get();
			int productQuantity;
			if (status.equalsIgnoreCase("decrease")) {
				System.out.println(status);
				productQuantity = cart.getQuantity()-1;
				if (productQuantity<=0) {
					cartRepository.delete(cart);
					return false;
				}
			}
			else {
				productQuantity = cart.getQuantity()+1;
			}
			cart.setQuantity(productQuantity);
			Cart save = cartRepository.save(cart);
			if (save!=null) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception e) {
			return false;
		}
	}

}
