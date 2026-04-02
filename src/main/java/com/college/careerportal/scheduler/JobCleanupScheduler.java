package com.college.careerportal.scheduler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.college.careerportal.entity.Opportunity;
import com.college.careerportal.repository.OpportunityRepository;

@Component
public class JobCleanupScheduler {

    @Autowired
    private OpportunityRepository opportunityRepository;

//    @Scheduled(fixedRate = 60000) // every 1 minute
//    public void deleteExpiredJobs() {
//
//        List<Opportunity> jobs = opportunityRepository.findAll();
//
//        for (Opportunity job : jobs) {
//
//            if (job.getDeadline() != null &&
//                job.getDeadline().isBefore(LocalDate.now())) {
//
//                opportunityRepository.delete(job);
//
//                System.out.println("Deleted expired job: " + job.getTitle());
//            }
//        }
//    }
    
    @Scheduled(fixedRate = 60000)
    public void deleteExpiredJobs() {
        opportunityRepository.deleteExpiredJobs();
        System.out.println("Expired jobs deleted automatically");
    }
}