package com.college.careerportal.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.college.careerportal.entity.Student;
import com.college.careerportal.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repo;

    public Student registerStudent(Student student) {
        return repo.save(student);
    }
    
    public List<Student> getAllStudents() {
        return repo.findAll();
    }
    
    public Student login(String email, String password) {
        return repo.findByEmailAndPassword(email, password);
    }
}