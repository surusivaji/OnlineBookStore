package org.siva.bookstore.repository;


import java.util.List;

import org.siva.bookstore.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Integer>{
	
	@Query("select book from Book book where book.bookCategory=:category and book.bookStatus='Active'")
	List<Book> findActiveBooksByCategory(String category);
	
	@Query("select book from Book book where book.bookStatus='Active' order by book.id desc")
	List<Book> findAllActiveBooks();
	
	@Query("select book from Book book where book.email=:email and book.bookCategory='Old' order by book.id desc")
	List<Book> findByEmailAndBookCategory(String email);
	
	List<Book> findByBookNameContainingIgnoreCaseOrAuthorNameContainingIgnoreCaseOrBookCategoryContainingIgnoreCase(String ch1, String ch2, String ch3);

}
