package com.college.careerportal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import com.college.careerportal.entity.Opportunity;
import com.college.careerportal.entity.Student;
import com.college.careerportal.entity.Application;
import com.college.careerportal.repository.StudentRepository;
import com.college.careerportal.service.OpportunityService;
import com.college.careerportal.service.ApplicationService;
import com.college.careerportal.service.StudentService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private StudentService service;
    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private StudentRepository studentRepository;

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
                         @RequestParam List<String> branches,
                         @RequestParam String description,
                         @RequestParam(required = false) String applyLink, 
                         @RequestParam List<String>  years,
                         @RequestParam LocalDate deadline){

        Opportunity op = new Opportunity();
        op.setTitle(title);
        op.setCompanyName(companyName);
        op.setType(type.toUpperCase()); // 🔥 normalize
        op.setDescription(description);
        op.setApplyLink(applyLink);
        op.setBranches(String.join(",", branches));
        op.setYears(String.join(",", years));
        op.setDeadline(deadline);

        opportunityService.addOpportunity(op);

        return "redirect:/opportunity/view";
    }
    
    @GetMapping("/applications")
    public String viewApplications(Model model) {

        List<Application> applications = applicationService.getAllApplications();
        model.addAttribute("applications", applications);

        model.addAttribute("totalApplications", applications.size());
        model.addAttribute("totalStudents", studentRepository.count());
        model.addAttribute("totalJobs", opportunityService.getAll().size());

        List<Object[]> companyBranchData = applicationService.getApplicationsByCompanyAndBranch();
        model.addAttribute("companyBranchData", companyBranchData);
        
        Map<String, Map<String, Long>> groupedData = new HashMap<>();

        for (Object[] row : companyBranchData) {
            String company = (String) row[0];
            String branch = (String) row[1];
            Long count = (Long) row[2];

            groupedData
                .computeIfAbsent(company, k -> new HashMap<>())
                .put(branch, count);
        }

        model.addAttribute("groupedData", groupedData);

        return "admin-applications";
    }
    
    
}