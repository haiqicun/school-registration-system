package com.metadata.schoolregistrationsystem.service.impl;

import com.metadata.schoolregistrationsystem.entity.Course;
import com.metadata.schoolregistrationsystem.repository.CourseRepository;
import com.metadata.schoolregistrationsystem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public ResponseEntity<List<Course>> getAllCourses() {
        return new ResponseEntity<>(courseRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Course> getCourseByID(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            return new ResponseEntity<>(course.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Course> createCourse(Course course) {
        Course courseSaved = new Course(course.getName());
        return new ResponseEntity<>(courseRepository.save(courseSaved), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Course> updateCourse(Course course) {
        Optional<Course> courseQuried = courseRepository.findById(course.getId());

        if (courseQuried.isPresent()) {
            Course courseSaved = courseQuried.get();
            courseSaved.setName(course.getName());
            return new ResponseEntity<>(courseRepository.save(courseSaved), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<HttpStatus> deleteCourse(Long id) {
        courseRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
