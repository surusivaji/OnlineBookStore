package org.siva.bookstore.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Full_Name", nullable=false, length=40)
	private String fullName;
	
	@Column(name="Email", nullable=false, length=50, unique=true)
	private String email;
	
	@Column(name="Mobile_Number", nullable=false, length=12, unique=true)
	private String mobileNumber;
	
	@Column(name="password", nullable=false, length=40)
	private String password;
	
	@Column(name="Profile_Image")
	private String profileImage;
	
}
