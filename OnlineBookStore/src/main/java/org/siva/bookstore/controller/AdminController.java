package org.siva.bookstore.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.siva.bookstore.models.Admin;
import org.siva.bookstore.models.Book;
import org.siva.bookstore.models.BookOrder;
import org.siva.bookstore.models.OrderStatus;
import org.siva.bookstore.models.User;
import org.siva.bookstore.service.IAdminService;
import org.siva.bookstore.service.IBookOrderService;
import org.siva.bookstore.service.IBookService;
import org.siva.bookstore.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private IAdminService adminService;
	
	@Autowired
	private IBookService bookService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IBookOrderService bookOrderService;
	
	@ModelAttribute
	public void commonData(HttpSession session, Model model) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			model.addAttribute("admin", admin);
		}
	}
	
	@PostMapping("/getAdminInformation")
	public String getAdminInformation(HttpSession session, @RequestParam String email, @RequestParam String password) {
		Admin admin = adminService.adminLogin(email, password);
		if (admin!=null) {
			session.setAttribute("admin", admin);
			return "redirect:/admin/home";
		} else {
			session.setAttribute("failMsg", "Invalid email and password");
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/home")
	public String adminHomePage(HttpSession session) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			return "/Admin/Home";
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/addBook")
	public String addBookPage(HttpSession session) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			return "/Admin/AddBook";
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@PostMapping("/saveBook")
	public String saveBookInformation(HttpSession session, @ModelAttribute Book book, @RequestParam("photo") MultipartFile multipartFile) {
		try {		
			Admin admin = (Admin) session.getAttribute("admin");
			if (admin!=null) {
				if (multipartFile.isEmpty()) {
					session.setAttribute("failMsg", "Please upload the image");
					return "redirect:/admin/addBook";
				}
				else {
					File file = new ClassPathResource("static/images").getFile();
					Path path = Paths.get(file.getAbsolutePath()+File.separator+"books"+File.separator+multipartFile.getOriginalFilename());
					Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
					book.setBookPhoto(multipartFile.getOriginalFilename());
				}
				book.setEmail(admin.getEmail());
				Book saveBook = bookService.SaveBook(book);
				if (saveBook!=null) {
					session.setAttribute("successMsg", "Book added successfully");
				}
				else {
					session.setAttribute("failMsg", "Something went wrong");
				}
				return "redirect:/admin/addBook";
			}
			else {
				return "redirect:/admin";
			}
		} catch (Exception e) {
			session.setAttribute("warningMsg", e.getMessage());
			return "redirect:/admin/addBook";
		}
	}
	
	@GetMapping("/viewBooks")
	public String displayBooks(HttpSession session, Model model, @RequestParam(defaultValue = "0") int pageNo) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			Page<Book> page = bookService.getAllBooks(pageNo);
			model.addAttribute("books", page.getContent());
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("totalBooks", page.getTotalElements());
			return "/Admin/ViewBooks";
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/editBook/{id}")
	public String editBookPage(HttpSession session, Model model, @PathVariable int id) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			Book book = bookService.getBookById(id);
			if (book!=null) {
				model.addAttribute("book", book);
				return "/Admin/EditBook";
			}
			else {
				session.setAttribute("failMsg", "Book not found");
				return "redirect:/admin/viewBooks";
			}
		} 
		else {
			return "redirect:/admin";
		}
	}
	
	@PostMapping("/updateBookInformation")
	public String updateBookInformation(HttpSession session, @RequestParam int id, @RequestParam String bookName, @RequestParam String authorName, @RequestParam double price, @RequestParam String bookStatus) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			Book book = bookService.getBookById(id);
			book.setBookName(bookName);
			book.setAuthorName(authorName);
			book.setPrice(price);
			book.setBookStatus(bookStatus);
			Book saveBook = bookService.SaveBook(book);
			if (saveBook!=null) {
				session.setAttribute("successMsg", "Book Information Updated");
			}
			else {
				session.setAttribute("failMsg", "Something went wrong");
			}
			return "redirect:/admin/viewBooks";
		} else {
			return "redirect:/admin";
		}
		
	}
	
	@GetMapping("/deleteBook/{id}")
	public String deleteBookInformation(HttpSession session, @PathVariable int id) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			Book book = bookService.getBookById(id);
			if (book!=null) {
				Boolean isDelete = bookService.deleteBook(book);
				if (isDelete) {
					session.setAttribute("successMsg", "Book information was deleted");
				}
				else {
					session.setAttribute("failMsg", "Something went wrong");
				}
			}
			else {
				session.setAttribute("warningMsg", "Book not found");
			}
			return "redirect:/admin/viewBooks";
		} else {
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/users")
	public String viewUsersPage(HttpSession session, Model model, @RequestParam(defaultValue="0") int pageNo) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			Page<User> page = userService.getAllUsers(pageNo);
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("users", page.getContent());
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("totalUsers", page.getTotalElements());
			return "/Admin/ViewUsers";
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/editUser/{id}")
	public String editUserPage(HttpSession session, Model model, @PathVariable int id) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			User user = userService.getUserById(id);
			if (user!=null) {
				model.addAttribute("user", user);
				return "/Admin/EditUser";
			}
			else {
				session.setAttribute("failMsg", "User information not found");
				return "redirect:/admin/users";
			}
		} else {
			return "redirect:/admin";
		}
	}
	
	@PostMapping("/updateUserInformation")
	public String updateUserInformation(HttpSession session, @ModelAttribute User user) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			User saveUser = userService.saveUser(user);
			if (saveUser!=null) {
				session.setAttribute("successMsg", "User information successfully updated");
				return "redirect:/admin/users";
			}
			else {
				session.setAttribute("failMsg", "Something went wrong");
				return "redirect:/admin/editUser/"+user.getId();
			}
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/deleteUser/{id}")
	public String deleteUserInformation(HttpSession session, @PathVariable int id) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			User user = userService.getUserById(id);
			if (user!=null) {
				Boolean deleteUser = userService.deleteUser(user);
				if (deleteUser) {
					session.setAttribute("successMsg", "User information deleted successfully");
				}
				else {
					session.setAttribute("failMsg", "Something went wrong");
				}
			}
			else {
				session.setAttribute("failMsg", "User information not found");
			}
			return "redirect:/admin/users";
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/addAdmin")
	public String addAdminPage(HttpSession session) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			return "/Admin/AddAdmin";
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@PostMapping("/saveAdminInformation")
	public String saveAdminInformation(HttpSession session, @RequestParam String fullName, 
															@RequestParam String email,
															@RequestParam String mobileNumber,
															@RequestParam String password,
															@RequestParam("adminImage") MultipartFile multipartFile) {
		Admin currentAdmin = (Admin) session.getAttribute("admin");
		if (currentAdmin!=null) {
			try {
				File file = new ClassPathResource("static/images").getFile();
				Path path = Paths.get(file.getAbsolutePath()+File.separator+"admins"+File.separator+multipartFile.getOriginalFilename());
				Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				Admin admin = new Admin();
				admin.setFullName(fullName);
				admin.setEmail(email);
				admin.setPassword(password);
				admin.setMobileNumber(mobileNumber);
				admin.setProfileImage(multipartFile.getOriginalFilename());
				Admin saveAdmin = adminService.saveAdmin(admin);
				if (saveAdmin!=null) {
					session.setAttribute("successMsg", "Admin added");
				}
				else {
					session.setAttribute("failMsg", "Something went wrong");
				}
				return "redirect:/admin/addAdmin";
			} catch (Exception e) {
				session.setAttribute("failMsg", "Image location not found");
				return "redirect:/admin/addAdmin";
			}
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/viewAdmins")
	public String viewAdminsPage(HttpSession session, Model model, @RequestParam(defaultValue="0") int pageNo) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			Page<Admin> page = adminService.getAllAdmins(pageNo);
			model.addAttribute("admins", page.getContent());
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("totalAdmins", page.getTotalElements());
			model.addAttribute("totalPages", page.getTotalPages());
			return "/Admin/ViewAdmins";
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/editAdmin/{id}")
	public String editAdminPage(HttpSession session, Model model, @PathVariable int id) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			Admin adminById = adminService.getAdminById(id);
			if (adminById!=null) {
				model.addAttribute("adminInfo", adminById);
				return "/Admin/EditAdmin";
			}
			else {
				session.setAttribute("failMsg", "Admin information not found");
				return "redirect:/admin/viewAdmins";
			}
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@PostMapping("/updateAdminInformation")
	public String updateAdminInformation(HttpSession session, @RequestParam int id,
															  @RequestParam String fullName,
															  @RequestParam String email,
															  @RequestParam String mobileNumber,
															  @RequestParam String password,
 															  @RequestParam("adminImage") MultipartFile multipartFile) {
		Admin currentAdmin = (Admin) session.getAttribute("admin");
		if (currentAdmin!=null) {
			try {
				Admin admin = new Admin();
				admin.setId(id);
				admin.setFullName(fullName);
				admin.setEmail(email);
				admin.setMobileNumber(mobileNumber);
				admin.setPassword(password);
				Admin oldAdmin = adminService.getAdminById(id);
				if (multipartFile.isEmpty()) {
					admin.setProfileImage(oldAdmin.getProfileImage());
				}
				else {
					File file = new ClassPathResource("static/images").getFile();
					Path path = Paths.get(file.getAbsolutePath()+File.separator+"admins"+File.separator+multipartFile.getOriginalFilename());
					Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
					admin.setProfileImage(multipartFile.getOriginalFilename());
				}
				Admin updateAdmin = adminService.saveAdmin(admin);
				if (updateAdmin!=null) {
					session.setAttribute("successMsg", "Admin information updated successfully");
				}
				else {
					session.setAttribute("failMsg", "Something went wrong");
				}
				return "redirect:/admin/viewAdmins";
			} catch (Exception e) {
				session.setAttribute("warningMsg", "Image is not found");
				return "redirect:/admin/editAdmin/"+id;
			}
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/deleteAdmin/{id}")
	public String deleteAdminInformation(HttpSession session, @PathVariable int id) {
		Admin currentAdmin = (Admin) session.getAttribute("admin");
		if (currentAdmin!=null) {
			Admin admin = adminService.getAdminById(id);
			if (admin!=null) {
				Boolean deleteAdmin = adminService.deleteAdmin(admin);
				if (deleteAdmin) {
					session.setAttribute("successMsg", "Admin information was removed");
				}
				else {
					session.setAttribute("failMsg", "Something went wrong");
				}
			}
			else {
				session.setAttribute("warningMsg", "Admin information not found");
			}
			return "redirect:/admin/viewAdmins";
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/orders")
	public String ordersPage(HttpSession session, Model model, @RequestParam(defaultValue="0") int pageNo) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			Page<BookOrder> page = bookOrderService.getAllBookOrders(pageNo);
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("bookOrders", page.getContent());
			return "/Admin/Orders";
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/deleteBookOrder/{id}")
	public String deleteBookOrderPage(HttpSession session, @PathVariable int id) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			BookOrder bookOrder = bookOrderService.getBookOrderByid(id);
			if (bookOrder!=null) {
				Boolean deleteBookOrder = bookOrderService.deleteBookOrder(bookOrder);
				if (deleteBookOrder) {
					session.setAttribute("successMsg", "Book Order Deleted Successfully");
				}
				else {
					session.setAttribute("failMsg", "Something went wrong");
				}
			}
			else {
				session.setAttribute("failMsg", "Book Order Not Found");
			}
			return "redirect:/admin/orders";
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@PostMapping("/updateBookOrderStatus")
	public String updateBookOrderStatus(HttpSession session, @RequestParam int id, @RequestParam("status") int st) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			OrderStatus[] values = OrderStatus.values();
			String status = null;
			for (OrderStatus orderStatus : values) {
				if (orderStatus.getId().equals(st)) {
					status = orderStatus.getName();
				}
			}
			Boolean isUpdated = bookOrderService.updateBookOrderStatus(id, status);
			if (isUpdated) {
				session.setAttribute("successMsg", "Book Order Status Updated Successfully");
			}
			else {
				session.setAttribute("failMsg", "Something Went Wrong");
			}
			return "redirect:/admin/orders";
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/logout")
	public String logoutPage(HttpSession session) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			session.removeAttribute("admin");
			session.setAttribute("logoutInfo", "You have been logout");
			return "redirect:/admin";
		} else {
			return "redirect:/admin";
		}
	}

}
