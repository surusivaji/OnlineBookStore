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
  <h4>1. Index Page</h4>
  <img src="index.png" alt="Index Page" width="80%">
  <h4>2. Registration Page</h4>
  <img src="register.png" alt="User Registration Page" width="80%">
   <h4>3. Login Page</h4>
  <img src="login.png" alt="User Login Page" width="80%">
   <h4>4. Forgot Password Page</h4>
  <img src="forgotpassword.png" alt="Forgot Password Page" Page" width="80%">
   <h4>5. Reset Password Page</h4>
  <img src="changepassword.png" alt="Reset Password Page" width="80%">
   <h4>6. Home Page</h4>
  <img src="userhome.png" alt="Home Page" width="80%">

---

<h3 align="center">🎉 Happy Shopping! 🚀</h3> 
<p align="center">Thank you for checking out this project! If you found it helpful, <b>give it a star ⭐ on GitHub! </b> 😊</p>
