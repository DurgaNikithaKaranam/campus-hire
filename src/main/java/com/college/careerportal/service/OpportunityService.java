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
}