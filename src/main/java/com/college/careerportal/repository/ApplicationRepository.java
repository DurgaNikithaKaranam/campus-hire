package com.college.careerportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.college.careerportal.entity.Application;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
	List<Application> findByStudentId(int studentId);
	Application findByStudentIdAndOpportunityId(int studentId, int opportunityId);
	
	@Query("""
			SELECT o.companyName, s.branch, COUNT(a)
			FROM Application a
			JOIN Student s ON a.studentId = s.id
			JOIN Opportunity o ON a.opportunityId = o.id
			GROUP BY o.companyName, s.branch
			""")
			List<Object[]> countApplicationsByCompanyAndBranch();
}