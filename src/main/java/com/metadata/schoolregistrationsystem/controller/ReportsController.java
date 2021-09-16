package com.metadata.schoolregistrationsystem.controller;

import com.metadata.schoolregistrationsystem.entity.Course;
import com.metadata.schoolregistrationsystem.entity.Student;
import com.metadata.schoolregistrationsystem.service.impl.ReportsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReportsController {
    private ReportsServiceImpl reportsService;

    public ReportsController(ReportsServiceImpl reportsService) {
        this.reportsService = reportsService;
    }

    @GetMapping("/reports/getStudentsByCourseId/{course_id}")
    public ResponseEntity<List<Student>> getStudentsByCourseID(@PathVariable("course_id") long courseId) {
        try {
            return reportsService.getAllStudentsByCourseID(courseId);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reports/getCoursesByStudentId/{student_id}")
    public ResponseEntity<List<Course>> getCoursesByStudentID(@PathVariable("student_id") long studentId) {
        try {
            return reportsService.getAllCoursesByStudentID(studentId);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reports/getCoursesWithNoStudent")
    public ResponseEntity<List<Course>> getCoursesWithNoStudent() {
        try {
            return reportsService.getAllCoursesWithNoStudents();
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reports/getStudentsWithNoCourse")
    public ResponseEntity<List<Student>> getStudentsWithNoCourse() {
        try {
            return reportsService.getAllStudentsWithNoCourses();
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
