<h1 align="center"> 📚 Online Book Store Application <h1>

<p align="center">
  <img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_MVC-6DB33F?style=for-the-badge&logo=spring&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Data_JPA-007396?style=for-the-badge&logo=spring&logoColor=white" />
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white" />
  <img src="https://img.shields.io/badge/HTML-FF5722?style=for-the-badge&logo=html5&logoColor=white" />
  <img src="https://img.shields.io/badge/CSS-264DE4?style=for-the-badge&logo=css3&logoColor=white" />
  <img src="https://img.shields.io/badge/JavaScript-F7E018?style=for-the-badge&logo=javascript&logoColor=black" />
  <img src="https://img.shields.io/badge/Bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white" />
</p>

---

## 📖 Overview 

The **Online Book Store Application** is a user-friendly platform designed for buying and selling books online.  
It simplifies the shopping process for customers while providing admins with tools to manage books, users, and orders efficiently.  
The application is built using **Spring MVC, Spring Boot, and Spring Data JPA**, ensuring seamless back-end integration and a responsive UI.  
With features like session management and pagination, the system is optimized for both **performance** and **scalability**.

---

## 🛠️ Tech Stack

- **Backend:** Java, Spring MVC, Spring Boot, Spring Data JPA  
- **Frontend:** HTML, CSS, JavaScript, Bootstrap  
- **Database:** MySQL  
- **Security:** Session Management  
- **Additional Features:** Pagination for managing large datasets  

---

## 👥 User Features

- 📝 **User Registration & Login** – Secure sign-up and login process  
- 🔒 **Forgot Password** – Reset password using email and mobile verification  
- 📚 **Browse Books** – View new, old, and recent books  
- 🔍 **Search** – Search books by title or category  
- 🛒 **Cart Management** – Add books to the cart, increase/decrease quantity, view total price  
- 💳 **Order Processing** – Place orders with address and payment options  
- 📦 **Order Management** – View, update, or delete orders  
- 👤 **Profile Management** – Update or delete profile  
- 📤 **Sell Old Books** – Add, edit, or delete your old book listings  

---

## 👨‍💼 Admin Features

- ➕ **Manage Books** – Add, update, view, or delete books  
- 📜 **Order Management** – View all orders, update order status, or delete orders  
- 👥 **User Management** – View, update, or delete user information  
- 🛠️ **Admin Management** – Add, view, or delete admins  
- 🔑 **Profile & Security** – Update profile or change password using email and old password  

---

## ⚙️ How the Application Works

The **project flow** of the application is as follows:

1. **User Registration & Login**  
   - New users register with their details, which are stored securely in the database.  
   - Returning users can log in, and sessions are created for secure navigation.  

2. **Browsing and Searching Books**  
   - Users can browse books by category (New, Old, or Recent) or use the search bar to quickly find specific books.  

3. **Cart and Checkout**  
   - Selected books are added to the shopping cart.  
   - Users can adjust quantities, view the total price, and proceed with checkout using their shipping address and payment preferences.  

4. **Order and Profile Management**  
   - Users can track their current and past orders.  
   - Profile details can be updated or deleted anytime for better account control.  

5. **Selling Old Books**  
   - Users can list old books for sale, update their details, or remove listings.  

6. **Admin Panel Operations**  
   - Admins log in to manage book inventory, handle orders, monitor user activities, and maintain system security with profile management.

---

## 🛠️ Setup & Installation

### 📋 Prerequisites
- ☕ **Java Development Kit (JDK) 8+**
- ⚙️ **Spring Boot**
- 🗄️ **MySQL Database**
- 🛠️ **Maven**
- 💻 **Spring Tool Suite (STS)**

### ⚙️ Steps to Run the Application

#### 1️⃣ Clone the Repository
```sh
git clone https://github.com/your-username/OnlineBookStore.git
cd OnlineBookStore
```

#### 2️⃣ Configure Database (MySQL)
- Create a database in MySQL:
  ```sql
  CREATE DATABASE online_book_store;
  ```
- Update `application.properties` with MySQL credentials:
  ```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/shopping_app
  spring.datasource.username=root
  spring.datasource.password=yourpassword
  spring.jpa.hibernate.ddl-auto=update
  ```

#### 3️⃣ Build the Project Using Maven
```sh
mvn clean install
```

#### 4️⃣ Run the Spring Boot Application
```sh
mvn spring-boot:run
```

#### 5️⃣ Access the Application
- **User Panel:** `http://localhost:8080/`
- **Admin Panel:** `http://localhost:8080/admin`

## 🖼️ Screenshots
<p>Below are sample screenshots showcasing the Shopping Application:</p>
<div>
  <h4>1. Index</h4>
  <img src="index.png" alt="Index Page" width="80%">
  <h4>2. Registration</h4>
  <img src="registeration.png" alt="User Registration Page" width="80%">
   <h4>3. Login</h4>
  <img src="login.png" alt="User Login Page" width="80%">
   <h4>4. Forgot Password</h4>
  <img src="forgotpassword.png" alt="Forgot Password Page" Page" width="80%">
   <h4>5. Reset Password</h4>
  <img src="changepassword.png" alt="Reset Password Page" width="80%">
   <h4>6. Home</h4>
  <img src="userhome.png" alt="Home Page" width="80%">
  <h4>7. User Profile</h4>
  <img src="profile.png" alt="Profile Page" width="80%">
  <h4>8. Recent Books</h4>
  <img src="recentbooks.png" alt="Recent Books Page" width="80%">
  <h4>9. Old Books</h4>
  <img src="oldbooks.png" alt="Old Books Page" width="80%">
  <h4>10. New Books</h4>
  <img src="newbooks.png" alt="New Books Page" width="80%">
  <h4>11. Search Book</h4>
  <img src="searchbook.png" alt="Search Book Page" width="80%">
  <h4>12. Display Old Book</h4>
  <img src="viewoldbook.png" alt="View Old Book Page" width="80%">
   <h4>13. Display New Book</h4>
  <img src="viewnewbook.png" alt="View New Book Page" width="80%">
   <h4>14. Display Cart</h4>
  <img src="viewcart.png" alt="View cart Page" width="80%">
  <h4>15. Fill Address</h4>
  <img src="filladdress.png" alt="Fill address Page" width="80%">
  <h4>16. Settings</h4>
  <img src="setting.png" alt="Settings " width="80%">
  <h4>17. My Orders</h4>
  <img src="myorders.png" alt="my orders " width="80%">
  <h4>18. Cancel Order</h4>
  <img src="cancelorder.png" alt="cancel order" width="80%">
  <h4>19. Delete Order</h4>
  <img src="deleteorder.png" alt="cancel order" width="80%">
  <h4>20. Sell Book</h4>
  <img src="sellbook.png" alt="sell book" width="80%">
  <h4>21. User Old Books</h4>
  <img src="useroldbooks.png" alt="user old books" width="80%">
  <h4>22. Delete User Old Book</h4>
  <img src="deleteuseroldbook.png" alt="delete user old books" width="80%">
  <h4>23. Edit User Profile</h4>
  <img src="editprofile.png" alt="edit user profile" width="80%">
  <h4>24. Delete User Account</h4>
  <img src="deleteaccount.png" alt="delete user account" width="80%"> 
  <h4>25. Contact Us</h4>
  <img src="contactus.png" alt="contact us" width="80%"> 
   <h4>26. User Logout</h4>
   <img src="logout.png" alt="logout" width="80%">   
    <h4>27. Admin Login</h4>
   <img src="admin.png" alt="admin" width="80%">   
    <h4>28. Admin Dashboard</h4>
   <img src="admindashboard.png" alt="admin dashboard" width="80%">   
   <h4>29. Admin Profile</h4>
   <img src="adminprofile.png" alt="admin profile" width="80%"> 
   <h4>30. Add Book</h4>
   <img src="addBook.png" alt="admin book" width="80%"> 
   <h4>31. Display Book</h4>
   <img src="displaybooks.png" alt="display books" width="80%"> 
    <h4>32. Edit Book</h4>
   <img src="displaybooks.png" alt="edit book" width="80%"> 
   <h4>33. Delete Book</h4>
   <img src="deletebook.png" alt="delete book" width="80%"> 
   <h4>34. Display Orders</h4>
   <img src="orders.png" alt="Orders" width="80%"> 
    <h4>35. Update Order Status</h4>
   <img src="bookorderstatus.png" alt="Book Order status" width="80%"> 
    <h4>36. Delete User Order</h4>
   <img src="deleteorder.png" alt="delete order" width="80%"> 
    <h4>37. Display Registered Users</h4>
   <img src="users.png" alt="users" width="80%">
    <h4>38. Update User Information</h4>
   <img src="edituser.png" alt="update user" width="80%">
  <h4>39. Update User Information</h4>
   <img src="edituser.png" alt="update user" width="80%">
  <h4>40. Delete User Information</h4>
  <img src="deleteuser.png" alt="delete user" width="80%">
   <h4>41. Add Admin</h4>
  <img src="addadmin.png" alt="add admin" width="80%">
    <h4>42. Display Admins Information</h4>
  <img src="admins.png" alt="admins" width="80%">
  <h4>43. Update Admin Information</h4>
  <img src="editadmin.png" alt="edit admin" width="80%">
   <h4>44. Delete Admin Account</h4>
    <img src="deleteadmin.png" alt="delete admin" width="80%">
    <h4>45. Admin Logout</h4>
    <img src="adminlogout.png" alt="admin logout" width="80%">

  
<hr/>

<h3 align="center">🎉 Happy Shopping! 🚀</h3> 
<p align="center">Thank you for checking out this project! If you found it helpful, <b>give it a star ⭐ on GitHub! </b> 😊</p>
