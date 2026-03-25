package com.college.careerportal.controller;

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

        int studentId = (int) session.getAttribute("userId");

        Student student = studentRepository.findById(studentId).orElse(null);

        List<Opportunity> list;

        if ("admin".equals(session.getAttribute("userRole"))) {
            // admin sees all jobs
            list = service.getAll();
        } else {
        	if (student.getYear() != null) {
        	    list = service.getFilteredJobs(student.getYear());
        	} else {
        	    list = service.getAll();
        	}
        }
        model.addAttribute("list", list);
        
        Integer sId = (Integer) session.getAttribute("userId");

        Map<Integer, Boolean> appliedMap = new HashMap<>();

        if (sId != null) {
            for (Opportunity op : list) {
                boolean applied = applicationService.alreadyApplied(sId, op.getId());
                appliedMap.put(op.getId(), applied);
            }
        }

        model.addAttribute("appliedMap", appliedMap);
        return "opportunities";
    }
    
    @GetMapping("/filter")
    public String filter(@RequestParam String type, Model model, HttpSession session) {

        List<Opportunity> list;

        if ("ALL".equals(type)) {
            list = service.getAllOpportunities();
        } else {
            list = service.filter(type);
        }

        model.addAttribute("list", list);

        // 🔥 role
        String role = (String) session.getAttribute("userRole");
        model.addAttribute("role", role);

        // 🔥 appliedMap
        Integer studentId = (Integer) session.getAttribute("userId");

        Map<Integer, Boolean> appliedMap = new HashMap<>();

        if (studentId != null) {
            for (Opportunity op : list) {
                boolean applied = applicationService.alreadyApplied(studentId, op.getId());
                appliedMap.put(op.getId(), applied);
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