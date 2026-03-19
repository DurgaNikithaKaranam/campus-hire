package com.college.careerportal.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.college.careerportal.entity.Student;
import com.college.careerportal.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService service;

    @PostMapping("/register")
    public Student register(@RequestBody Student student) {
        return service.registerStudent(student);
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
    public String login(@RequestParam String email, @RequestParam String password) {

        Student student = service.login(email, password);

        if (student != null) {
            return "Login Successful!";
        } else {
            return "Invalid Credentials!";
        }
    }
}