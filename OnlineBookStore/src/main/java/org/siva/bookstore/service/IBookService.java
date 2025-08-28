package org.siva.bookstore.service;

import java.util.List;

import org.siva.bookstore.models.Book;
import org.springframework.data.domain.Page;

public interface IBookService {
	
	Book SaveBook(Book book);
	
	Page<Book> getAllBooks(int pageNo);
	
	Book getBookById(int id);
	
	Boolean deleteBook(Book book);
	
	List<Book> getNewBooks();
	
	List<Book> getOldBooks();
	
	List<Book> getRecentBooks();

	List<Book> getOldBooksByEmail(String email);
	
	List<Book> searchBooks(String ch);
	
}
