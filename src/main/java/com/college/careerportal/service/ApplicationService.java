package com.college.careerportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.college.careerportal.entity.Application;
import com.college.careerportal.repository.ApplicationRepository;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository repo;
    

    public Application apply(Application app) {
        app.setStatus("APPLIED");
        return repo.save(app);
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
}