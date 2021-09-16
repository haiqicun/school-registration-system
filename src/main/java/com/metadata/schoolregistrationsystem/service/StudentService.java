package com.metadata.schoolregistrationsystem.service;

import com.metadata.schoolregistrationsystem.entity.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudentService {
    ResponseEntity<List<Student>> getAllStudents();
    ResponseEntity<Student> getStudentByID(Long id);
    ResponseEntity<Student> createStudent(Student student);
    ResponseEntity<Student> updateStudent(Student student);
    ResponseEntity<HttpStatus> deleteStudent(Long id);
    ResponseEntity<Student> registerCourse(Long studentId, Long courseId);
}
