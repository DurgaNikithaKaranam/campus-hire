package com.college.careerportal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.college.careerportal.entity.Application;
import com.college.careerportal.entity.Opportunity;
import com.college.careerportal.repository.StudentRepository;
import com.college.careerportal.service.ApplicationService;
import com.college.careerportal.service.OpportunityService;
import com.college.careerportal.service.StudentService;


@RestController
@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    private ApplicationService service;
    @Autowired
    private OpportunityService opportunityService;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private StudentRepository studentRepository;
    

    @PostMapping("/apply")
    public String apply(@RequestParam int opportunityId,
                        @RequestParam int studentId) {

        if (applicationService.alreadyApplied(studentId, opportunityId)) {
            return "Already Applied";
        }

        Application app = new Application();
        app.setStudentId(studentId);
        app.setOpportunityId(opportunityId);
        app.setStatus("APPLIED");

        applicationService.apply(app);

        return "Applied Successfully";
    }
    
    // View all applications (Admin)
    @GetMapping("/all")
    @ResponseBody 
    public List<Application> getAll() {
        return service.getAllApplications();
    }
    
    @PutMapping("/updateStatus")
    public String updateStatus(@RequestParam int id,
                               @RequestParam String status) {

        service.updateStatus(id, status);
        return "updated";
    }
 
    
    @GetMapping("/myIds")
    @ResponseBody
    public List<Integer> getMyAppliedIds(@RequestParam int studentId) {

        List<Application> apps = applicationService.getByStudent(studentId);

        List<Integer> ids = new ArrayList<>();

        for (Application app : apps) {
            ids.add(app.getOpportunityId());
        }

        return ids;
    }
    
    @GetMapping("/api/my/{studentId}")
    @ResponseBody
    public List<Application> getMyApplications(@PathVariable int studentId) {
        return applicationService.getByStudentId(studentId);
    }
    
}