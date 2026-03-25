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

    public List<Student> getAllStudents() {
        return repo.findAll();
    }
    
    public String registerStudent(Student student) {

        // check email already exists
        Student existing = repo.findByEmail(student.getEmail());

        if (existing != null) {
            return "EXISTS";
        }

        student.setRole("student");  // 🔥 important
        repo.save(student);

        return "SUCCESS";
    }
    
    public Student login(String email, String password) {
        return repo.findByEmailAndPassword(email, password);
    }
}