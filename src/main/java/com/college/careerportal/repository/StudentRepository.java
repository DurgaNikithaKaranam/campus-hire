package com.college.careerportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.college.careerportal.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>  {
	Student findByEmailAndPassword(String email, String password);
}
