package org.siva.bookstore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.siva.bookstore.models.Book;
import org.siva.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IBookServiceImpl implements IBookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public Book SaveBook(Book book) {
		try {
			Book save = bookRepository.save(book);
			return save;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Page<Book> getAllBooks(int pageNo) {
		try {
			Pageable pageable = PageRequest.of(pageNo, 5);
			Page<Book> page = bookRepository.findAll(pageable);
			return page;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Book getBookById(int id) {
		try {
			Optional<Book> optional = bookRepository.findById(id);
			return optional.get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Boolean deleteBook(Book book) {
		try {
			bookRepository.delete(book);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public List<Book> getNewBooks() {
		try {
			List<Book> newBooks = bookRepository.findActiveBooksByCategory("New");
			return newBooks;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<Book> getOldBooks() {
		try {
		  	List<Book> oldBooks = bookRepository.findActiveBooksByCategory("Old");
		  	return oldBooks;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<Book> getRecentBooks() {
		try {
			List<Book> recentBooks = bookRepository.findAllActiveBooks();
			return recentBooks;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<Book> getOldBooksByEmail(String email) {
		try {
			List<Book> books = bookRepository.findByEmailAndBookCategory(email);
			return books;
		}
		catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<Book> searchBooks(String ch) {
		try {
			List<Book> books = bookRepository.findByBookNameContainingIgnoreCaseOrAuthorNameContainingIgnoreCaseOrBookCategoryContainingIgnoreCase(ch, ch, ch);
			List<Book> activeBooks = new ArrayList<Book>();
			for (Book book : books) {
				if (book.getBookStatus().equalsIgnoreCase("Active")) {
					activeBooks.add(book);
				}
			}
			return activeBooks;
		} catch (Exception e) {
			return null;
		}
	}
	

}
