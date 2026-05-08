package com.college.careerportal.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.college.careerportal.entity.Student;
import com.college.careerportal.service.ApplicationService;
import com.college.careerportal.service.OpportunityService;
import com.college.careerportal.service.StudentService;

//import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService service;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private OpportunityService opportunityService;
    
    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return service.getAllStudents();
    }
    
    //from here for react 
    
    @PostMapping("/api/login")
    public Map<String, Object> loginApi(@RequestBody Student student) {

        Student s = service.login(student.getEmail(), student.getPassword());

        Map<String, Object> res = new HashMap<>();

        if (s == null) {
            res.put("status", "error");
            res.put("message", "Invalid credentials");
            return res;
        }

        res.put("status", "success");
        res.put("user", s);

        return res;
    }
    
    @PostMapping("/api/register")
    public String register(@RequestBody Student student) {

        String result = service.registerStudent(student);

        if ("EXISTS".equals(result)) {
            return "Email already registered ❌";
        }

        return "Registered Successfully ✔";
    }
    
    @GetMapping("/api/dashboard/{id}")
    public Map<String, Object> getDashboard(@PathVariable int id) {

        Map<String, Object> data = new HashMap<>();

        int totalApplications = applicationService.countByStudent(id);
        int selected = applicationService.countSelectedByStudent(id);
        int jobs = opportunityService.getAll().size();

        data.put("totalApplications", totalApplications);
        data.put("selected", selected);
        data.put("jobs", jobs);

        return data;
    }
    

}