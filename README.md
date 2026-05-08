# 🎓 Career Portal System

A full-stack Career Portal web application developed using React.js and Spring Boot to manage internships and job opportunities for students.

---

## 🚀 Features

### 👨‍🎓 Student Module
- Student Registration & Login
- View Eligible Opportunities
- Apply for Jobs/Internships
- Track Application Status
- View Profile

### 👨‍💼 Admin Module
- Admin Login
- Add Job Opportunities
- Manage Jobs
- View Applications
- Dashboard Analytics
- Send Email Notifications to Eligible Students

---

## 🛠️ Tech Stack

### Frontend
- React.js
- JavaScript
- HTML
- CSS

### Backend
- Spring Boot
- Java
- REST APIs

### Database
- MySQL

### Other Tools
- Git & GitHub
- Maven
- JavaMailSender (Email Notifications)

---

## 🔗 Frontend & Backend Communication

The frontend communicates with the backend using REST APIs.

Example:

```js
fetch("http://localhost:8080/api/opportunities")
```

React sends HTTP requests and Spring Boot returns JSON responses.

---

## 📧 Email Notification Feature

When an admin adds a new job opportunity:
- Eligible students are filtered based on branch and year
- Email notifications are automatically sent using JavaMailSender

---

## ⚙️ How to Run the Project

### Backend

```bash
mvn spring-boot:run
```

Runs on:

```text
http://localhost:8080
```

### Frontend

```bash
cd frontend
npm install
npm start
```

Runs on:

```text
http://localhost:3000
```

---

## 👩‍💻 Author

Durga Nikitha Karanam
