package org.siva.bookstore.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.siva.bookstore.models.BookOrder;
import org.siva.bookstore.models.Cart;
import org.siva.bookstore.models.OrderAddress;
import org.siva.bookstore.models.OrderRequest;
import org.siva.bookstore.models.OrderStatus;
import org.siva.bookstore.models.User;
import org.siva.bookstore.repository.BookOrderRepository;
import org.siva.bookstore.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IBookOrderServiceImpl implements IBookOrderService {
	
	@Autowired
	private BookOrderRepository bookOrderRepository;
	
	@Autowired
	private CartRepository cartRepository;

	@Override
	public Boolean saveBookOrder(OrderRequest orderRequest, User user) {
		try {
			List<Cart> cartList = cartRepository.findByUser(user);
			boolean isSaved = false;
			for (Cart cart : cartList) {
				BookOrder bookOrder = new BookOrder();
				Random random = new Random();
				long orderId = random.nextLong(10000000000L);
				if (orderId<10000000000L) {
					orderId = orderId+10000000000L;
				}
				bookOrder.setOrderId(String.valueOf(orderId));
				bookOrder.setOrderDate(Date.valueOf(LocalDate.now()));
				bookOrder.setBook(cart.getBook());
				bookOrder.setQuantity(cart.getQuantity());
				bookOrder.setPrice(cart.getBook().getPrice());
				bookOrder.setUser(user);
				bookOrder.setStatus(OrderStatus.IN_PROGRESS.getName());
				bookOrder.setPaymentType(orderRequest.getPaymentType());
				OrderAddress orderAddress = new OrderAddress();
				orderAddress.setFirstName(orderRequest.getFirstName());
				orderAddress.setLastName(orderRequest.getLastName());
				orderAddress.setEmail(orderRequest.getEmail());
				orderAddress.setMobileNumber(orderRequest.getMobileNumber());
				orderAddress.setCity(orderRequest.getCity());
				orderAddress.setAddress(orderRequest.getAddress());
				orderAddress.setState(orderRequest.getState());
				orderAddress.setPincode(orderRequest.getPinCode());
				bookOrder.setOrderAddress(orderAddress);
				BookOrder save = bookOrderRepository.save(bookOrder);
				if (save!=null) {
					isSaved = true;
				}
			}
			if (isSaved) {
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
	public List<BookOrder> getBookOrderByUser(User user) {
		try {
			List<BookOrder> bookOrders = bookOrderRepository.findByUser(user);
			return bookOrders;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public BookOrder getBookOrderByOrderId(String orderId) {
		try {
			BookOrder bookOrder = bookOrderRepository.findByOrderId(orderId);
			return bookOrder;
		}
		catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Boolean updateBookOrderStatus(Integer orderId, String status) {
		try {
			BookOrder bookOrder = bookOrderRepository.findById(orderId).get();
			bookOrder.setStatus(status);
			BookOrder save = bookOrderRepository.save(bookOrder);
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
	
	@Override
	public BookOrder getBookOrderByid(int id) {
		try {
			Optional<BookOrder> optional = bookOrderRepository.findById(id);
			return optional.get();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Boolean deleteBookOrder(BookOrder bookOrder) {
		try {
			bookOrderRepository.delete(bookOrder);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public Page<BookOrder> getAllBookOrders(int pageNo) {
		try {
			Pageable pageable = PageRequest.of(pageNo, 10);
			Page<BookOrder> page = bookOrderRepository.findAll(pageable);
			return page;
		} catch (Exception e) {
			return null;
		}
	}

}
