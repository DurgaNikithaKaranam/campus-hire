package com.college.careerportal.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
public class Application {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int studentId;

    // ✅ FIX HERE
    @Column(name = "opportunity_id")
    private int opportunityId;

    private String status;

    // ✅ relation mapping
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "opportunity_id", insertable = false, updatable = false)
    private Opportunity opportunity;
    // Getters and Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getOpportunityId() { return opportunityId; }
    public void setOpportunityId(int opportunityId) { this.opportunityId = opportunityId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}