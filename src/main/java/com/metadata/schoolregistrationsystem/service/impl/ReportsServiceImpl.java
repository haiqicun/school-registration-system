package com.metadata.schoolregistrationsystem.service.impl;

import com.metadata.schoolregistrationsystem.entity.Course;
import com.metadata.schoolregistrationsystem.entity.Student;
import com.metadata.schoolregistrationsystem.repository.CourseRepository;
import com.metadata.schoolregistrationsystem.repository.StudentRepository;
import com.metadata.schoolregistrationsystem.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportsServiceImpl implements ReportsService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public ResponseEntity<List<Student>> getAllStudentsByCourseID(Long courseId) {
        Optional<Course> courseQueried = courseRepository.findById(courseId);

        if (courseQueried.isPresent()) {
            Course course = courseQueried.get();
            return new ResponseEntity<>(new ArrayList<Student>(course.getStudents()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<Course>> getAllCoursesByStudentID(Long studentId) {
        Optional<Student> studentQueried = studentRepository.findById(studentId);

        if (studentQueried.isPresent()) {
            Student student = studentQueried.get();
            return new ResponseEntity<>(new ArrayList<Course>(student.getCourses()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<Course>> getAllCoursesWithNoStudents() {
        List<Course> courses = courseRepository.findAll().stream()
                .filter(c -> c.getStudents().size() == 0).collect(Collectors.toList());

        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Student>> getAllStudentsWithNoCourses() {
        List<Student> students = studentRepository.findAll().stream()
                .filter(s -> s.getCourses().size() == 0).collect(Collectors.toList());

        return new ResponseEntity<>(students, HttpStatus.OK);
    }
}
