package org.siva.bookstore.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Book_Name", nullable=false, length=40)
	private String bookName;
	
	@Column(name="Author_Name", nullable=false, length=40)
	private String authorName;
	
	@Column(name="Book_Category", nullable=false, length=40)
	private String bookCategory;
	
	@Column(name="Book_Price", nullable=false)
	private Double price;
	
	@Column(name="Book_Status", nullable=false)
	private String bookStatus;
	
	@Column(name="Book_Photo", nullable=false)
	private String bookPhoto;
	
	@Column(name="User_Email", nullable=false)
	private String email;

}
