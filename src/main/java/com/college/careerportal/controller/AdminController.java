package com.college.careerportal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
//import org.springframework.ui.Model;

import com.college.careerportal.entity.Opportunity;
import com.college.careerportal.entity.Student;
import com.college.careerportal.entity.Application;
import com.college.careerportal.repository.StudentRepository;
import com.college.careerportal.service.OpportunityService;
import com.college.careerportal.service.ApplicationService;
import com.college.careerportal.service.MailService;
import com.college.careerportal.service.StudentService;
//
//import jakarta.servlet.http.HttpSession;

@RestController
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
    
    @Autowired
    private MailService mailService;

    @PostMapping("/add-job")
    public String addJob(@RequestParam String title,
                         @RequestParam String companyName,
                         @RequestParam String type,
                         @RequestParam List<String> branches,
                         @RequestParam String description,
                         @RequestParam(required = false) String applyLink,
                         @RequestParam List<String> years,
                         @RequestParam String deadline) {

        Opportunity op = new Opportunity();
        op.setTitle(title);
        op.setCompanyName(companyName);
        op.setType(type.toUpperCase());
        op.setDescription(description);
        op.setApplyLink(applyLink);
        op.setBranches(String.join(",", branches));
        op.setYears(String.join(",", years));
        op.setDeadline(LocalDate.parse(deadline));

        opportunityService.addOpportunity(op);

        // 🔥 CONVERT DATA
        List<String> branchList = branches.stream()
                .map(String::toUpperCase)
                .toList();

        List<Integer> yearList = years.stream()
                .map(Integer::parseInt)
                .toList();

        // 🔥 GET ELIGIBLE STUDENTS
        List<Student> students = studentRepository.findEligibleStudents(branchList, yearList);

        // 🔥 SEND MAILS
        for (Student s : students) {

        	String message =
        		    "A new job opportunity has been posted on Career Portal.\n\n" +
        		    "Company: " + companyName + "\n" +
        		    "Role: " + title + "\n" +
        		    "Deadline: " + deadline + "\n\n" +
        		    "Login to your account to apply.\n\n" +
        		    "Regards,\nCareer Portal Team";

            mailService.sendMail(
                    s.getEmail(),
                    "New Job Opportunity - " + companyName,
                    message
            );
        }

        return "success";
    }
    

    @GetMapping("/api/dashboard")
    public Map<String, Object> getDashboardData() {

        Map<String, Object> data = new HashMap<>();

        List<Application> applications = applicationService.getAllApplications();

        data.put("totalApplications", applications.size());
        data.put("totalStudents", studentRepository.count());
        data.put("totalJobs", opportunityService.getAll().size());

        List<Object[]> companyBranchData = applicationService.getApplicationsByCompanyAndBranch();

        data.put("companyBranchData", companyBranchData);

        return data;
    }
    
    
}