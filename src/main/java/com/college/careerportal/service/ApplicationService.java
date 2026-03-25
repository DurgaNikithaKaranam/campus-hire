package com.college.careerportal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.college.careerportal.entity.Application;
import com.college.careerportal.entity.Opportunity;
import com.college.careerportal.entity.Student;
import com.college.careerportal.repository.ApplicationRepository;
import com.college.careerportal.repository.OpportunityRepository;
import com.college.careerportal.repository.StudentRepository;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository repo;
    
    @Autowired
    private StudentRepository studentRepository;
    

    public String apply(Application app) {

    	try {
            Application existing = repo.findByStudentIdAndOpportunityId(
                    app.getStudentId(),
                    app.getOpportunityId()
            );

            if (existing != null) {
                return "Already Applied!";
            }

            app.setStatus("APPLIED");
            repo.save(app);

            return "Application Submitted!";

        } catch (Exception e) {
            e.printStackTrace();  // 🔥 IMPORTANT
            return "Error occurred";
        }
    }
    @Autowired
    private OpportunityRepository opportunityRepository;

    public List<String> getApplicationDetails(int studentId) {

    	List<Application> apps = repo.findByStudentId(studentId);
        List<String> details = new ArrayList<>();

        for (Application app : apps) {

            Opportunity op = opportunityRepository.findById(app.getOpportunityId()).orElse(null);

            if (op != null) {
                String data = op.getCompanyName() + " - " + op.getTitle() + " (" + app.getStatus() + ")";
                details.add(data);
            }
        }

        return details;
    }
    
    public List<Application> getAllApplications() {
        return repo.findAll();
    }

    public Application updateStatus(int id, String status) {

        return repo.findById(id).map(app -> {
            app.setStatus(status);
            return repo.save(app);
        }).orElse(null);
    }
    
    public List<Application> getByStudent(int studentId) {
        return repo.findByStudentId(studentId);
    }
    
    public boolean alreadyApplied(int studentId, int opportunityId) {
        return repo.findByStudentIdAndOpportunityId(studentId, opportunityId) != null;
    }
    
    public List<String> getAdminApplicationDetails() {

        List<Application> apps = repo.findAll();

        List<String> details = new ArrayList<>();

        for (Application app : apps) {

            Student s = studentRepository.findById(app.getStudentId()).orElse(null);
            Opportunity op = opportunityRepository.findById(app.getOpportunityId()).orElse(null);

            if (s != null && op != null) {
                String data = "ID:" + app.getId() + " | " +
                              s.getName() + " | " +
                              op.getCompanyName() + " - " + op.getTitle() +
                              " | " + app.getStatus();
                details.add(data);
            }
        }

        return details;
    }
}