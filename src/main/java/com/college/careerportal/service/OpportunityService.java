package com.college.careerportal.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.college.careerportal.entity.Opportunity;
import com.college.careerportal.repository.OpportunityRepository;

@Service
public class OpportunityService {

    @Autowired
    private OpportunityRepository repo;

    public Opportunity addOpportunity(Opportunity op) {
        return repo.save(op);
    }

    public List<Opportunity> getAllOpportunities() {
        return repo.findAll();
    }
    
    public List<Opportunity> getByType(String type) {
        return repo.findByType(type);
    }
    
    public Opportunity getById(int id) {
        return repo.findById(id).orElse(null);
    }
    
    public void deleteOpportunity(int id) {
        repo.deleteById(id);
    }
    public List<Opportunity> filter(String type) {
        return repo.findByType(type);
    }
    public List<Opportunity> getFilteredJobs(int studentYear) {

        return repo.findByYear(studentYear);
    }

    public List<Opportunity> getAll() {
        return repo.findAll();
    }
}