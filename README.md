# 🏦 Stock Trading System

## 📘 Project Overview
The **Stock Trading System** is a full-stack web application designed to simulate a real-world stock trading environment.  
It allows users to register, log in, view live stock data, manage their trading portfolios, and perform stock transactions (buy/sell).  
This system provides a secure and interactive platform for users to experience the fundamentals of trading and investment management.

---

## 🚀 Features

### 👤 User Management
- User Registration and Secure Login (with Spring Security)
- Encrypted password storage
- User-specific profile and portfolio

### 📈 Trading Operations
- View available stocks with their **current rates** and **quantities**
- Buy and Sell stocks with real-time price updates
- Track total invested amount and remaining balance
- Update portfolio automatically after every transaction

### 💰 Portfolio & Balance
- Display user’s wallet balance
- Maintain transaction history (buy/sell logs)
- Show profit/loss based on market value

### 📊 Admin Features (optional)
- Add new stocks with initial price and quantity
- Update stock details
- Monitor all users’ activities

---

## 🛠️ Tech Stack

### **Backend**
- **Java**
- **Spring Boot** (REST APIs)
- **Spring Security** (Authentication & Authorization)
- **Hibernate / JPA** (ORM)
- **MySQL** (Database)

### **Frontend**
- **HTML5**, **CSS3**, **JavaScript**
- Responsive design for a clean trading dashboard

**📂 Project Structure**
Stock Trading System/
│
├── backend/
│   ├── src/main/java/com/example/employeemanagement/
│   │   ├── controller/
│   │   ├── entity/
│   │   ├── repository/
│   │   ├── service/
│   │   └── config/
│   └── resources/
│       └── application.properties
│
└── frontend/
    ├── html
    ├── css
    └── js

