package com.college.careerportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.college.careerportal.entity.Application;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
	List<Application> findByStudentId(int studentId);
	Application findByStudentIdAndOpportunityId(int studentId, int opportunityId);
}