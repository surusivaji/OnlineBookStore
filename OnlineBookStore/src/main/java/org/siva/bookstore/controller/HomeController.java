package org.siva.bookstore.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.siva.bookstore.models.Book;
import org.siva.bookstore.models.User;
import org.siva.bookstore.service.IBookService;
import org.siva.bookstore.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IBookService bookService;
	
	@GetMapping("/")
	public String indexPage(Model model) {
		List<Book> newBooks = bookService.getNewBooks();
		newBooks = newBooks.stream().limit(4).toList();
		model.addAttribute("newBooks", newBooks);
		List<Book> oldBooks = bookService.getOldBooks();
		oldBooks = oldBooks.stream().limit(4).toList();
		model.addAttribute("oldBooks", oldBooks);
		List<Book> recentBooks = bookService.getRecentBooks();
		recentBooks = recentBooks.stream().limit(4).toList();
		model.addAttribute("recentBooks", recentBooks);
		return "Index";
	}
	
	@GetMapping("/signup")
	public String registrationPage(Model model) {
		model.addAttribute("title", "E-Books | Registration");
		return "Registration";
	}
	
	@GetMapping("/signin")
	public String loginPage(Model model) {
		model.addAttribute("title", "E-Books | Login");
		return "Login";
	}
	
	@GetMapping("/admin")
	public String adminLogin(Model model) {
		model.addAttribute("title", "E-Books | Admin Login");
		return "AdminLogin";
	}
	
	@PostMapping("/saveUserInformation")
	public String saveUserInformation(@ModelAttribute User user, HttpSession session, @RequestParam MultipartFile userImage) {
		try {
			if (userImage.isEmpty()) {
				session.setAttribute("warningMsg", "Please upload the image");
				return "redirect:/signup";
			}
			File file = new ClassPathResource("static/images").getFile();
			Path path = Paths.get(file.getAbsolutePath()+File.separator+"users"+File.separator+userImage.getOriginalFilename());
			Files.copy(userImage.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			user.setProfileImage(userImage.getOriginalFilename());
			User isSave = userService.saveUser(user);
			if (isSave!=null) {
				session.setAttribute("successMsg", "Registration successfully Completed");
				return "redirect:/signup";
			} else {
				session.setAttribute("failMsg", "Something went wrong");
				return "redirect:/signup";
			}
		} catch (Exception e) {
			session.setAttribute("warningMsg", "Image is not found");
			return "redirect:/signup";
		}
	}
	
	@GetMapping("/recentBooks")
	public String recentBooksPage(Model model) {
		model.addAttribute("title", "E-Books | Recent Books");
		List<Book> recentBooks = bookService.getRecentBooks();
		model.addAttribute("recentBooks", recentBooks);
		return "RecentBooks";
	}
	
	@GetMapping("/newBooks")
	public String newBooksPage(Model model) {
		model.addAttribute("title", "E-Books | New Books");
		List<Book> newBooks = bookService.getNewBooks();
		model.addAttribute("newBooks", newBooks);
		return "NewBooks";
	}
	
	@GetMapping("/oldBooks")
	public String getOldBooksPage(Model model) {
		model.addAttribute("title", "E-Books | Old Books");
		List<Book> oldBooks = bookService.getOldBooks();
		model.addAttribute("oldBooks", oldBooks);
		return "OldBooks";
	}
	
	@GetMapping("/viewBook/{id}")
	public String viewBookPage(Model model, @PathVariable int id) {
		Book book = bookService.getBookById(id);
		if (book!=null) {
			model.addAttribute("title", "E-Books | "+book.getBookName());
			model.addAttribute("book", book);
			return "ViewBook";
		}
		else {
			return "redirect:/";
		}
	}
	
	@GetMapping("/searchBook")
	public String searchBookPage(Model model, @RequestParam String ch) {
		List<Book> books = bookService.searchBooks(ch);
		books = books.stream().sorted(Comparator.comparing(Book::getId).reversed()).collect(Collectors.toList());
		model.addAttribute("recentBooks", books);
		return "RecentBooks";
	}
	
	@GetMapping("/forgotPassword")
	public String forgotPasswordPage(Model model) {
		model.addAttribute("title", "E-Books | Forgot Password");
		return "ForgotPassword";
	}
	
	@PostMapping("/checkEmailAndMobileNumber")
	public String checkEmailAndMobileNumber(Model model, String email, String mobileNumber, HttpSession session) {
		User user = userService.getUserByEmailAndMobileNumer(email, mobileNumber);
		if (user!=null) {
			session.setAttribute("userInfo", user);
			return "redirect:/next";
		}
		else {
			session.setAttribute("failMsg", "Invalid information");
			return "redirect:/forgotPassword";
		}
	}
	
	@GetMapping("/next")
	public String changePasswordPage(HttpSession session, Model model) {
		User user = (User) session.getAttribute("userInfo");
		if (user!=null) {
			model.addAttribute("title", "E-Books | Change Password");
			return "ChangePassword";
		}
		else {
			return "redirect:/forgotPassword";
		}
	}
	
	@PostMapping("/passwordUpdation")
	public String updatePassword(HttpSession session, String password, String confirmPassword) {
		User user = (User) session.getAttribute("userInfo");
		if (user!=null) {
			if (password.equals(confirmPassword)) {
				user.setPassword(password);
				User saveUser = userService.saveUser(user);
				if (saveUser!=null) {
					session.setAttribute("warningMsg", "Password updated successfully");
				}
				else {
					session.setAttribute("failMsg", "Something went Wrong");
				}
				session.removeAttribute("userInfo");
				return "redirect:/signin";
			}
			else {
				session.setAttribute("failMsg", "Both Passwords Most Be Same");
				return "redirect:/next";
			}
		}
		else {
			return "redirect:/signin";
		}
	}
	
}
