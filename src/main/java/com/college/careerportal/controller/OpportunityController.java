package com.college.careerportal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.college.careerportal.entity.Opportunity;
import com.college.careerportal.entity.Student;
import com.college.careerportal.repository.StudentRepository;
import com.college.careerportal.service.ApplicationService;
import com.college.careerportal.service.OpportunityService;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
@RequestMapping("/opportunity")
public class OpportunityController {

    @Autowired
    private OpportunityService service;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private StudentRepository studentRepository;

    // Add opportunity (Admin)
    @PostMapping("/add")
    public String addOpportunity(@ModelAttribute Opportunity op, HttpSession session) {

        String role = (String) session.getAttribute("userRole");

        if (!"admin".equals(role)) {
            return "redirect:/student/dashboard";
        }

        service.addOpportunity(op);

        return "redirect:/opportunity/view";
    }

    // View opportunities (Student)
    @GetMapping("/all")
    public List<Opportunity> getAll() {
        return service.getAllOpportunities();
    }
    
    @GetMapping("/view")
    public String viewJobs(HttpSession session, Model model) {

        Integer studentId = (Integer) session.getAttribute("userId");
        String role = (String) session.getAttribute("userRole");
        if (studentId == null && "student".equals(role)) {
            return "redirect:/login";
        }

        Student student = null;

        if ("student".equals(role) && studentId != null) {
            student = studentRepository.findById(studentId).orElse(null);
        }
        
        List<Opportunity> list;

        // 👨‍💼 ADMIN → see all jobs
        if ("admin".equals(role)) {
            list = service.getAll();
        } else {

        	if ("student".equals(role) && studentId != null) {
                student = studentRepository.findById(studentId).orElse(null);
            }
            
            List<Opportunity> all = service.getAll();
            list = new ArrayList<>();

            for (Opportunity op : all) {

                if (student != null && op.getBranches() != null && op.getYears() != null) {

                    String studentBranch = student.getBranch().toUpperCase();
                    String studentYear = String.valueOf(student.getYear());

                    boolean branchMatch = false;
                    boolean yearMatch = false;

                    // 🔥 BRANCH CHECK
                    for (String b : op.getBranches().split(",")) {
                        if (b.trim().equalsIgnoreCase(studentBranch)) {
                            branchMatch = true;
                            break;
                        }
                    }

                    // 🔥 YEAR CHECK
                    for (String y : op.getYears().split(",")) {
                        if (y.trim().equals(studentYear)) {
                            yearMatch = true;
                            break;
                        }
                    }

                    if (branchMatch && yearMatch) {
                        list.add(op);
                    }
                }
            }
        }

        // 🔥 appliedMap logic (same as yours)
        Map<Integer, Boolean> appliedMap = new HashMap<>();

        if (studentId != null) {
            for (Opportunity op : list) {
                boolean applied = applicationService.alreadyApplied(studentId, op.getId());
                appliedMap.put(op.getId(), applied);
            }
        }

        model.addAttribute("appliedMap", appliedMap);
        model.addAttribute("list", list);

        return "opportunities";
    }

 
    
    @GetMapping("/filter")
    public String filter(@RequestParam String type, Model model, HttpSession session) {

        String role = (String) session.getAttribute("userRole");

        // 🔥 Step 1: Type filter
        List<Opportunity> filtered;

        if ("ALL".equalsIgnoreCase(type)) {
            filtered = service.getAll();
        } else {
            filtered = service.filterByType(type);
        }

        List<Opportunity> list = new ArrayList<>();

        // 👨‍💼 ADMIN → no further filtering
        if ("admin".equals(role)) {
            list = filtered;
        }

        // 👨‍🎓 STUDENT → apply branch/year filter
        else {

            Integer studentId = (Integer) session.getAttribute("userId");
            Student student = studentRepository.findById(studentId).orElse(null);

            if (student != null) {

                String studentBranch = student.getBranch().toUpperCase();
                String studentYear = String.valueOf(student.getYear());

                for (Opportunity op : filtered) {

                    if (op.getBranches() != null && op.getYears() != null) {

                        boolean branchMatch = false;
                        boolean yearMatch = false;

                        // 🔥 branch check
                        for (String b : op.getBranches().split(",")) {
                            if (b.trim().equalsIgnoreCase(studentBranch)) {
                                branchMatch = true;
                                break;
                            }
                        }

                        // 🔥 year check
                        for (String y : op.getYears().split(",")) {
                            if (y.trim().equals(studentYear)) {
                                yearMatch = true;
                                break;
                            }
                        }

                        if (branchMatch && yearMatch) {
                            list.add(op);
                        }
                    }
                }
            }
        }

        model.addAttribute("list", list);

        // 🔥 appliedMap (only for students)
        Map<Integer, Boolean> appliedMap = new HashMap<>();

        if ("student".equals(role)) {
            Integer sId = (Integer) session.getAttribute("userId");

            if (sId != null) {
                for (Opportunity op : list) {
                    boolean applied = applicationService.alreadyApplied(sId, op.getId());
                    appliedMap.put(op.getId(), applied);
                }
            }
        }

        model.addAttribute("appliedMap", appliedMap);

        return "opportunities";
    }
    
    @GetMapping("/delete")
    public String delete(@RequestParam int id, HttpSession session) {

        String role = (String) session.getAttribute("userRole");

        if (!"admin".equals(role)) {
            return "redirect:/opportunity/view";  // ❌ block access
        }

        service.deleteOpportunity(id);
        return "redirect:/opportunity/view";
    }
}