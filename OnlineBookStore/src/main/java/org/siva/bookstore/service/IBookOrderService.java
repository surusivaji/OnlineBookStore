package org.siva.bookstore.service;

import java.util.List;

import org.siva.bookstore.models.BookOrder;
import org.siva.bookstore.models.OrderRequest;
import org.siva.bookstore.models.User;
import org.springframework.data.domain.Page;

public interface IBookOrderService {
	
	Boolean saveBookOrder(OrderRequest orderRequest, User user);
	
	List<BookOrder> getBookOrderByUser(User user);
	
	BookOrder getBookOrderByOrderId(String orderId);

	Boolean updateBookOrderStatus(Integer orderId, String status);
	
	BookOrder getBookOrderByid(int id);
	
	Boolean deleteBookOrder(BookOrder bookOrder);
	
	Page<BookOrder> getAllBookOrders(int pageNo);
	

}
