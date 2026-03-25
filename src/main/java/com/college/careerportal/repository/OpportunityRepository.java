package com.college.careerportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.college.careerportal.entity.Opportunity;
import java.util.List;

public interface OpportunityRepository extends JpaRepository<Opportunity, Integer> {
	List<Opportunity> findByType(String type);
	List<Opportunity> findByYear(int year);
}