package com.college.careerportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.college.careerportal.entity.Opportunity;
import com.college.careerportal.service.OpportunityService;

@RestController
@RequestMapping("/opportunity")
public class OpportunityController {

    @Autowired
    private OpportunityService service;

    // Add opportunity (Admin)
    @GetMapping("/add")
    public String addOpportunity() {

        Opportunity op = new Opportunity();
        op.setTitle("Java Internship");
        op.setType("INTERNSHIP");
        op.setCompanyName("Infosys");
        op.setBranch("CSE");
        op.setDescription("Java Developer Internship");
        op.setApplyType("EXTERNAL"); // change to INTERNAL if needed
        op.setApplyLink("https://infosys.com/careers");
        service.addOpportunity(op);

        return "Opportunity Added!";
    }

    // View opportunities (Student)
    @GetMapping("/all")
    public List<Opportunity> getAll() {
        return service.getAllOpportunities();
    }
}