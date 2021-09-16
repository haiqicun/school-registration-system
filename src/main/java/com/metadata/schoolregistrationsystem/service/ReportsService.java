package com.metadata.schoolregistrationsystem.service;

import com.metadata.schoolregistrationsystem.entity.Course;
import com.metadata.schoolregistrationsystem.entity.Student;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReportsService {
    ResponseEntity<List<Student>> getAllStudentsByCourseID(Long courseId);
    ResponseEntity<List<Course>> getAllCoursesByStudentID(Long studentId);
    ResponseEntity<List<Course>> getAllCoursesWithNoStudents();
    ResponseEntity<List<Student>> getAllStudentsWithNoCourses();
}
