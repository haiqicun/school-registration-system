package com.metadata.schoolregistrationsystem.service;

import com.metadata.schoolregistrationsystem.entity.Course;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CourseService {
    ResponseEntity<List<Course>> getAllCourses();
    ResponseEntity<Course> getCourseByID(Long id);
    ResponseEntity<Course> createCourse(Course course);
    ResponseEntity<Course> updateCourse(Course course);
    ResponseEntity<HttpStatus> deleteCourse(Long id);
}
