package com.college.careerportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.college.careerportal.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
}