package com.college.careerportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.college.careerportal.entity.Opportunity;

import jakarta.transaction.Transactional;

import java.util.List;

public interface OpportunityRepository extends JpaRepository<Opportunity, Integer> {
	List<Opportunity> findByType(String type);
	List<Opportunity> findByTypeIgnoreCase(String type);
	
	@Modifying
    @Transactional
    @Query("DELETE FROM Opportunity o WHERE o.deadline < CURRENT_DATE")
    void deleteExpiredJobs();
}