package com.college.careerportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.college.careerportal.entity.Opportunity;

public interface OpportunityRepository extends JpaRepository<Opportunity, Integer> {
}