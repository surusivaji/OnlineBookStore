package org.siva.bookstore.service;

import java.util.Optional;

import org.siva.bookstore.models.Admin;
import org.siva.bookstore.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IAdminServiceImpl implements IAdminService {
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public Admin saveAdmin(Admin admin) {
		try {
			Admin save = adminRepository.save(admin);
			return save;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Admin adminLogin(String email, String password) {
		try {
			Admin admin = adminRepository.findByEmailAndPassword(email, password);
			return admin;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Page<Admin> getAllAdmins(int pageNo) {
		try {
			Pageable pagable = PageRequest.of(pageNo, 3);
			Page<Admin> page = adminRepository.findAll(pagable);
			return page;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Admin getAdminById(int id) {
		try {
			Optional<Admin> optional = adminRepository.findById(id);
			return optional.get();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Boolean deleteAdmin(Admin admin) {
		try {
			adminRepository.delete(admin);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
