package org.siva.bookstore.repository;

import java.util.List;

import org.siva.bookstore.models.Book;
import org.siva.bookstore.models.Cart;
import org.siva.bookstore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
	
	Cart findByBookAndUser(Book book, User user);
	
	List<Cart> findByUser(User user);
	
	Long countCartByUser(User user);
	
}
