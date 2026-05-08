package com.college.careerportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import org.springframework.data.repository.query.Param;

import com.college.careerportal.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>  {
	Student findByEmailAndPassword(String email, String password);
	Student findByEmail(String email);
	@Query("""
		    SELECT s FROM Student s 
		    WHERE UPPER(s.branch) IN :branches 
		    AND s.year IN :years
		""")
		List<Student> findEligibleStudents(
		    @Param("branches") List<String> branches,
		    @Param("years") List<Integer> years
		);
	
}
