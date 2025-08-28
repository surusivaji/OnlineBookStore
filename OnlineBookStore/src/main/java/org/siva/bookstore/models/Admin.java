package org.siva.bookstore.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Admin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Full_Name", nullable=false, length=50)
	private String fullName;
	
	@Column(name="Email", nullable=false, length=50, unique=true)
	private String email;
	
	@Column(name="Mobile_Number", nullable=false, length=15, unique=true)
	private String mobileNumber;
	
	@Column(name="Password", nullable=false, length=20)
	private String password;
	
	@Column(name="Profile_Image", nullable=false)
	private String profileImage;

}
