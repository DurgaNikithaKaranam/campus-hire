package com.college.careerportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.college.careerportal.entity.Application;
import com.college.careerportal.service.ApplicationService;

@RestController
@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    private ApplicationService service;

    // Student applies
    @GetMapping("/apply")
    public String apply() {

        Application app = new Application();
        app.setStudentId(1);       // existing student ID
        app.setOpportunityId(1);   // existing opportunity ID

        service.apply(app);

        return "Application Submitted!";
    }

    // View all applications (Admin)
    @GetMapping("/all")
    public List<Application> getAll() {
        return service.getAllApplications();
    }
    
    @GetMapping("/updateStatus")
    public String updateStatus(@RequestParam int id, @RequestParam String status) {

        Application app = service.updateStatus(id, status);

        if (app != null) {
            return "Application status updated to " + status;
        } else {
            return "Application not found";
        }
    }
}