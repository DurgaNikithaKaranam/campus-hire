package com.college.careerportal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.college.careerportal.entity.Application;
import com.college.careerportal.entity.Opportunity;
import com.college.careerportal.service.ApplicationService;
import com.college.careerportal.service.OpportunityService;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    private ApplicationService service;
    @Autowired
    private OpportunityService opportunityService;
    @Autowired
    private ApplicationService applicationService;
    
    // Student applies
    @GetMapping("/apply")
    public String apply(@RequestParam int opportunityId, HttpSession session) {

        Integer studentId = (Integer) session.getAttribute("userId");

        if (applicationService.alreadyApplied(studentId, opportunityId)) {
            return "redirect:/opportunity/view";  // ❌ block duplicate
        }

        Application app = new Application();
        app.setStudentId(studentId);
        app.setOpportunityId(opportunityId);
        app.setStatus("APPLIED");

        service.apply(app);

        return "redirect:/opportunity/view";
    }

    // View all applications (Admin)
    @GetMapping("/all")
    @ResponseBody 
    public List<Application> getAll() {
        return service.getAllApplications();
    }
    
//    @GetMapping("/updateStatus") 
//    public String updateStatus(@RequestParam int id,
//                               @RequestParam String status) {
//
//        service.updateStatus(id, status);
//
//        return "redirect:/application/admin";
//    }
    
    @GetMapping("/my")
    public String myApplications(HttpSession session, Model model) {

        int studentId = (int) session.getAttribute("userId");

        model.addAttribute("details", service.getApplicationDetails(studentId));

        return "myapplications";
    }  
    
    @GetMapping("/admin")
    public String adminView(Model model) {

        model.addAttribute("list", service.getAdminApplicationDetails());

        return "admin-applications";
    }
}