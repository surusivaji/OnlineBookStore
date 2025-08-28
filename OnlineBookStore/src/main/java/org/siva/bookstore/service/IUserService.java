package org.siva.bookstore.service;

import org.siva.bookstore.models.User;
import org.springframework.data.domain.Page;

public interface IUserService {
	
	User saveUser(User user);
	
	User getUserByEmailAndPassword(String email, String password);
	
	Page<User> getAllUsers(int pageNo);
	
	User getUserById(int id);
	
	Boolean deleteUser(User user);
	
	User getUserByEmailAndMobileNumer(String email, String mobileNumber);

}
