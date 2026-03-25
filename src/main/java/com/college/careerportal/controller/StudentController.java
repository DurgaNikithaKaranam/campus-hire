package com.college.careerportal.controller;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.college.careerportal.entity.Student;
import com.college.careerportal.service.StudentService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService service;

    @PostMapping("/register")
    public String register(@ModelAttribute Student student, Model model) {

        String result = service.registerStudent(student);

        if ("EXISTS".equals(result)) {
            model.addAttribute("error", "Email already registered ❌");
            return "register";
        }

        model.addAttribute("msg", "Registration successful ✔ Please login");
        return "login";
    }
    
    @GetMapping("/registerPage")
    public String registerPage() {
        return "register";
    }
    
    @GetMapping("/test")
    public String test() {
        return "API is working!";
    }
    
    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return service.getAllStudents();
    }
    
    @GetMapping("/add")
    public String addStudent() {
        Student s = new Student();
        s.setName("Nikitha");
        s.setEmail("nikitha@gmail.com");
        s.setPassword("1234");
        s.setRole("student");
        s.setBranch("CSE");
        s.setResumePath("resume.pdf");

        service.registerStudent(s);
        return "Student Added Successfully!";
    }
    

    
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
    
    @GetMapping("/loginPage")
    public String loginPage(@RequestParam String email,
                            @RequestParam String password,
                            Model model,
                            HttpSession session) {

        Student student = service.login(email, password);

        if (student == null) {
            model.addAttribute("error", "Invalid email or password ❌");
            return "login";
        }

        // store session
        session.setAttribute("userId", student.getId());
        session.setAttribute("userName", student.getName());
        session.setAttribute("userRole", student.getRole());

        // redirect based on role
        if ("admin".equals(student.getRole())) {
            return "redirect:/admin/dashboard";
        }

        return "redirect:/student/dashboard";
    } 
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        if (session.getAttribute("userId") == null) {
            return "redirect:/student/login";
        }

        Integer studentId = (Integer) session.getAttribute("userId");

        model.addAttribute("name", session.getAttribute("userName"));

        // 🔥 dummy values for now (later dynamic)
        model.addAttribute("totalApplications", 5);
        model.addAttribute("selectedCount", 1);
        model.addAttribute("jobCount", 10);

        return "dashboard";
    }
    
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/student/login";
    }
}