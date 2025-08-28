package org.siva.bookstore.service;

import java.util.Optional;

import org.siva.bookstore.models.User;
import org.siva.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IUserServiceImpl implements IUserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User saveUser(User user) {
		try {
			User save = userRepository.save(user);
			return save;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public User getUserByEmailAndPassword(String email, String password) {
		try {
			User user = userRepository.findByEmailAndPassword(email, password);
			return user;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public User getUserById(int id) {
		try {
			Optional<User> optional = userRepository.findById(id);
			return optional.get();
		} catch (Exception e) {
			return null;
		}
	}

@	Override
	public Page<User> getAllUsers(int pageNo) {
		try {
			Pageable pageable = PageRequest.of(pageNo, 8);
			Page<User> page = userRepository.findAll(pageable);
			return page;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Boolean deleteUser(User user) {
		try {
			userRepository.delete(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public User getUserByEmailAndMobileNumer(String email, String mobileNumber) {
		try {
			User user = userRepository.findByEmailAndMobileNumber(email, mobileNumber);
			return user;
		}
		catch (Exception e) {
			return null;
		}
	}
	
}
