package com.college.careerportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import com.college.careerportal.entity.Opportunity;
import com.college.careerportal.entity.Student;
import com.college.careerportal.service.OpportunityService;
import com.college.careerportal.service.StudentService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private StudentService service;
    @Autowired
    private OpportunityService opportunityService;

    // Open login page
    @GetMapping("/login")
    public String loginPage() {
        return "admin-login";
    }

    // Handle login
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        Student admin = service.login(email, password);

        if (admin != null && "admin".equals(admin.getRole())) {
            session.setAttribute("userRole", "admin");   // 🔥 IMPORTANT
            return "admin-dashboard";
        }

        model.addAttribute("error", "Invalid admin credentials");
        return "admin-login";
    }

    // Dashboard
    @GetMapping("/dashboard")
    public String adminDashboard(HttpSession session, Model model) {

        if (session.getAttribute("userId") == null) {
            return "redirect:/student/login";
        }

        // 🔥 dummy values (we will make dynamic later)
        model.addAttribute("jobCount", 10);
        model.addAttribute("applicationCount", 20);
        model.addAttribute("studentCount", 5);

        return "admin-dashboard";
    }
    
    @GetMapping("/addPage")
    public String showAddPage() {
        return "add-job";
    }

    @PostMapping("/add-job")
    public String addJob(@RequestParam String title,
                         @RequestParam String companyName,
                         @RequestParam String type,
                         @RequestParam String branch,
                         @RequestParam String description,
                         @RequestParam(required = false) String applyLink, 
                         @RequestParam Integer year){

        Opportunity op = new Opportunity();
        op.setTitle(title);
        op.setCompanyName(companyName);
        op.setType(type);
        op.setBranch(branch);
        op.setDescription(description);
        op.setApplyLink(applyLink);
        op.setYear(year);

        opportunityService.addOpportunity(op);

        return "redirect:/opportunity/view";
    }
}