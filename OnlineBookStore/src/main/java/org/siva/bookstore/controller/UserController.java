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
import org.siva.bookstore.models.BookOrder;
import org.siva.bookstore.models.Cart;
import org.siva.bookstore.models.OrderRequest;
import org.siva.bookstore.models.OrderStatus;
import org.siva.bookstore.models.User;
import org.siva.bookstore.service.IBookOrderService;
import org.siva.bookstore.service.IBookService;
import org.siva.bookstore.service.ICartService;
import org.siva.bookstore.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IBookService bookService;
	
	@Autowired
	private ICartService cartService;
	
	@Autowired
	private IBookOrderService bookOrderService;
	
	@ModelAttribute
	public void commonData(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		Long totalCarts = cartService.countCartByUser(user);
		model.addAttribute("totalCarts", totalCarts);
	}
	
	@PostMapping("/getUserInformation")
	public String getUserInformation(HttpSession session, @RequestParam String email, @RequestParam String password) {
		User user = userService.getUserByEmailAndPassword(email, password);
		if (user!=null) {
			session.setAttribute("user", user);
			return "redirect:/user/home";
		}
		else {
			session.setAttribute("failMsg", "Invalid email and password");
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/home")
	public String homePage(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			List<Book> newBooks = bookService.getNewBooks();
			newBooks = newBooks.stream().limit(4).toList();
			model.addAttribute("newBooks", newBooks);
			List<Book> oldBooks = bookService.getOldBooks();
			oldBooks = oldBooks.stream().limit(4).toList();
			model.addAttribute("oldBooks", oldBooks);
			List<Book> recentBooks = bookService.getRecentBooks();
			recentBooks = recentBooks.stream().limit(4).toList();
			model.addAttribute("recentBooks", recentBooks);
			return "/User/Home";
		}
		else {
			return "redirect:/signin";
		}	
	}
	
	@GetMapping("/recentBooks")
	public String recentBooksPage(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			List<Book> recentBooks = bookService.getRecentBooks();
			model.addAttribute("recentBooks", recentBooks);
			return "/User/RecentBooks";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/newBooks")
	public String newBooksPage(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			List<Book> newBooks = bookService.getNewBooks();
			model.addAttribute("newBooks", newBooks);
			return "/User/NewBooks";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/oldBooks")
	public String oldBooksPage(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			List<Book> oldBooks = bookService.getOldBooks();
			model.addAttribute("oldBooks", oldBooks);
			return "/User/OldBooks";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/viewBook/{id}")
	public String viewBookPage(HttpSession session, Model model, @PathVariable int id) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			Book book = bookService.getBookById(id);
			if (book!=null) {
				model.addAttribute("book", book);
				return "/User/ViewBook";
			}
			else {
				return "redirect:/user/home";
			}
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/addToCart")
	public String addToCart(HttpSession session, @RequestParam int uid, @RequestParam int bid) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			Boolean isSave = cartService.addToCart(uid, bid);
			if (isSave!=null) {
				session.setAttribute("successMsg", "Cart added successfully");
			}
			else {
				session.setAttribute("failMsg", "Something went wrong");
			}
			return "redirect:/user/viewBook/"+bid;
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/viewCart")
	public String viewCartPage(HttpSession session, Model  model) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			List<Cart> carts = cartService.getCartsByUser(user);
			if (carts.size()>0) {
				double totalOrderPrice = carts.get(carts.size()-1).getTotalOrderPrice();
				model.addAttribute("totalOrderPrice", totalOrderPrice);
			}
			model.addAttribute("carts", carts);
			return "/User/ViewCart";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/cartQuantityUpdate")
	public String cartQuantityUpdate(HttpSession session, @RequestParam String status, @RequestParam("cid") int id) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			Boolean updateQuantity = cartService.updateQuantity(status, id);
			if (updateQuantity) {
				System.out.println("quantity update");
			}
			else {
				System.out.println("Something went wrong");
			}
			return "redirect:/user/viewCart";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/setting")
	public String settingPage(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			return "/User/Setting";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/helpLineService")
	public String helpLineServicePage(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			return "/User/HelpLine";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/sellBook")
	public String sellBookPage(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			return "/User/SellBook";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/editProfile")
	public String editProfilePage(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			return "/User/EditProfile";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@PostMapping("/sellOldBook")
	public String sellOldBook(HttpSession session, @RequestParam String bookName,
												   @RequestParam String authorName,
												   @RequestParam double price,
												   @RequestParam MultipartFile bookImage) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			try {
				File file = new ClassPathResource("static/images").getFile();
				Path path = Paths.get(file.getAbsolutePath()+File.separator+"books"+File.separator+bookImage.getOriginalFilename());
				Files.copy(bookImage.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				Book book = new Book();
				book.setBookName(bookName);
				book.setAuthorName(authorName);
				book.setPrice(price);
				book.setBookPhoto(bookImage.getOriginalFilename());
				book.setBookCategory("Old");
				book.setBookStatus("Active");
				book.setEmail(user.getEmail());
				Book saveBook = bookService.SaveBook(book);
				if (saveBook!=null) {
					session.setAttribute("successMsg", "Book Added Successfully");
				}
				else {
					session.setAttribute("failMsg", "Something went wrong");
				}
				return "redirect:/user/sellBook";
			} catch (Exception e) {
				session.setAttribute("warningMsg", "Image is not found 🤯🤯🤯");
				return "redirect:/user/sellBook";
			}
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@PostMapping("/updateUserInformation")
	public String updateUserInformation(HttpSession session, @ModelAttribute User newUserInfo, @RequestParam("userImage") MultipartFile multipartFile) {
		try {
			User user = (User) session.getAttribute("user");
			if (user!=null) {
				User oldUser = userService.getUserById(newUserInfo.getId());
				if (multipartFile.isEmpty()) {
					newUserInfo.setProfileImage(oldUser.getProfileImage());
				}
				else {
					File file = new ClassPathResource("static/images").getFile();
					Path path = Paths.get(file.getAbsolutePath()+File.separator+"users"+File.separator+multipartFile.getOriginalFilename());
					Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
					newUserInfo.setProfileImage(multipartFile.getOriginalFilename());
				}
				User saveUser = userService.saveUser(newUserInfo);
				if (saveUser!=null) {
					session.setAttribute("user", saveUser);
					session.setAttribute("successMsg", "User information updated successfully");
				}
				else {
					session.setAttribute("failMsg", "Something went wrong");
				}
				return "redirect:/user/editProfile";
			}
			else {
				return "redirect:/signin";
			}	
		} catch (Exception e) {
			session.setAttribute("failMsg", "Something went wrong");
			return "redirect:/user/editProfile";
		}
	}
	
	@GetMapping("/yourOldBooks")
	public String yourOldBooksPage(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			List<Book> userOldBooks = bookService.getOldBooksByEmail(user.getEmail());
			model.addAttribute("userOldBooks", userOldBooks);
			return "/User/YourOldBooks";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/deleteOldBook/{id}")
	public String deleteOldBook(HttpSession session, @PathVariable int id) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			Book book = bookService.getBookById(id);
			if (book!=null) {
				Boolean deleteBook = bookService.deleteBook(book);
				if (deleteBook) {
					session.setAttribute("successMsg", "Book information deleted");
				}
				else {
					session.setAttribute("failMsg", "Something went wrong");
				}
			}
			else {
				session.setAttribute("warningMsg", "Book Information Not Found");
			}
			return "redirect:/user/yourOldBooks";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/order")
	public String orderPage(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			List<Cart> carts = cartService.getCartsByUser(user);
			if (carts.size()>0) {
				double orderPrice = carts.get(carts.size()-1).getTotalOrderPrice();
				model.addAttribute("orderPrice", orderPrice);
				double totalOrderPrice = orderPrice+50+50;
				model.addAttribute("totalOrderPrice", totalOrderPrice);
			}
			return "/User/Order";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@PostMapping("/orderStatus")
	public String orderStatusPage(HttpSession session, @ModelAttribute OrderRequest orderRequest) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			Boolean saveBookOrder = bookOrderService.saveBookOrder(orderRequest, user);
			if (saveBookOrder) {
				return "redirect:/user/orderSuccess";
			}
			else {
				session.setAttribute("failMsg", "Something went wrong");
				return "redirect:/user/order";
			}
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/orderSuccess") 
	public String successMsg(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			return "/User/Success";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/myOrders")
	public String myOrdersPage(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			List<BookOrder> bookOrders = bookOrderService.getBookOrderByUser(user);
			model.addAttribute("bookOrders", bookOrders);
			return "/User/MyOrders";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/updateBookOrderStatus")
	public String updateOrderStatusPage(HttpSession session, @RequestParam Integer orderId, @RequestParam("status") Integer st) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			OrderStatus[] values = OrderStatus.values();
			String status = null;
			for (OrderStatus orderStatus : values) {
				if (orderStatus.getId().equals(st)) {
					status = orderStatus.getName();
				}
			}
			Boolean isUpdated = bookOrderService.updateBookOrderStatus(orderId, status);
			if (isUpdated) {
				session.setAttribute("successMsg", "Order status updated");
			}
			else {
				session.setAttribute("failMsg", "Something went wrong");
			}
			return "redirect:/user/myOrders";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/deleteBookOrder/{id}")
	public String deleteBookOrder(HttpSession session, @PathVariable int id) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
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
				session.setAttribute("failMsg", "Book Order not Found");
			}
			return "redirect:/user/myOrders";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/searchBook") 
	public String searchBook(HttpSession session, @RequestParam String ch, Model model) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			List<Book> books = bookService.searchBooks(ch);
			books = books.stream().sorted(Comparator.comparing(Book::getId).reversed()).collect(Collectors.toList());
			model.addAttribute("recentBooks", books);
			return "/User/RecentBooks";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/deleteAccount")
	public String deleteAccount(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			return "/User/DeleteAccount";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@PostMapping("/accountDeletion")
	public String accountDeletion(HttpSession session, @RequestParam String email, @RequestParam String password) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
				Boolean deleteUser = userService.deleteUser(user);
				if (deleteUser) {
					session.removeAttribute("user");
					session.setAttribute("warningMsg", "Your account deleted permunantly");
					return "redirect:/signin";
				}
				else {
					session.setAttribute("failMsg", "Something went wrong");
					return "redirect:/user/deleteAccount";
				}
			}
			else {
				session.setAttribute("failMsg", "Invalid credientials");
				return "redirect:/user/deleteAccount";
			}
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			session.removeAttribute("user");
			session.setAttribute("logoutInfo", "logout success");
			return "redirect:/signin";
		}
		else {
			return "redirect:/signin";
		}
	}
	
}
