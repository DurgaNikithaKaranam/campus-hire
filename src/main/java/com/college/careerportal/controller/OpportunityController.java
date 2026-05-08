package com.college.careerportal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.college.careerportal.entity.Opportunity;
import com.college.careerportal.entity.Student;
import com.college.careerportal.repository.StudentRepository;
import com.college.careerportal.service.ApplicationService;
import com.college.careerportal.service.OpportunityService;


import java.time.LocalDate;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class OpportunityController {

    @Autowired
    private OpportunityService service;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private StudentRepository studentRepository;
    
    @GetMapping("/opportunities")
    public List<Opportunity> getAllJobs() {

        System.out.println("OPPORTUNITIES API HIT");

        List<Opportunity> list = service.getAll();

        System.out.println("SIZE: " + list.size());

        return list;
    }
    
    @GetMapping("/student-opportunities/{studentId}")
    public List<Opportunity> getStudentOpportunities(@PathVariable int studentId) {

        Student student = studentRepository.findById(studentId).orElse(null);

        if (student == null) return new ArrayList<>();

        List<Opportunity> all = service.getAll();
        List<Opportunity> filtered = new ArrayList<>();

        String studentBranch = student.getBranch().trim().toUpperCase();
        String studentYear = String.valueOf(student.getYear()).trim();

        for (Opportunity op : all) {

            if (op.getBranches() == null || op.getYears() == null) continue;

            boolean branchMatch = false;
            boolean yearMatch = false;

            // 🔥 BRANCH MATCH
            for (String b : op.getBranches().split(",")) {
                if (b.trim().equalsIgnoreCase(studentBranch)) {
                    branchMatch = true;
                    break;
                }
            }

            // 🔥 YEAR MATCH
            for (String y : op.getYears().split(",")) {
                if (y.trim().equals(studentYear)) {
                    yearMatch = true;
                    break;
                }
            }

            // ✅ FINAL CONDITION
            if (branchMatch && yearMatch) {
                filtered.add(op);
            }
        }

        return filtered;
    }
    
    @DeleteMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam int id) {
    	System.out.println("DELETE API CALLED: " + id);
        service.deleteOpportunity(id);

        return "Deleted";
    }
    
    @PostMapping("/add")
    public Opportunity addOpportunity(@RequestBody Opportunity op) {

        System.out.println("ADDING JOB: " + op.getTitle());

        return service.addOpportunity(op);
    }

}