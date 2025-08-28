package org.siva.bookstore.repository;

import java.util.List;

import org.siva.bookstore.models.BookOrder;
import org.siva.bookstore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookOrderRepository extends JpaRepository<BookOrder, Integer> {
	
	List<BookOrder> findByUser(User user);
	
	BookOrder findByOrderId(String orderId);

}
