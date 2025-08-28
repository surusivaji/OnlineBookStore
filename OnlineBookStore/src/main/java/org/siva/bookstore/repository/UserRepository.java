package org.siva.bookstore.repository;

import org.siva.bookstore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByEmailAndPassword(String email, String password);
	
	User findByEmailAndMobileNumber(String email, String mobielNumber);

}
