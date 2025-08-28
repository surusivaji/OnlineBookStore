package org.siva.bookstore.service;

import org.siva.bookstore.models.Admin;
import org.springframework.data.domain.Page;

public interface IAdminService {
	
	Admin saveAdmin(Admin admin);
	
	Admin adminLogin(String email, String password);
	
	Page<Admin> getAllAdmins(int pageNo);
	
	Admin getAdminById(int id);
	
	Boolean deleteAdmin(Admin admin);

}
