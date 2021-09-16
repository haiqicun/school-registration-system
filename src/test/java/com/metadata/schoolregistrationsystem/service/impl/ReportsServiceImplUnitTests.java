package com.metadata.schoolregistrationsystem.service.impl;

import com.metadata.schoolregistrationsystem.TestData;
import com.metadata.schoolregistrationsystem.entity.Course;
import com.metadata.schoolregistrationsystem.entity.Student;
import com.metadata.schoolregistrationsystem.repository.CourseRepository;
import com.metadata.schoolregistrationsystem.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ReportsServiceImplUnitTests {

    @InjectMocks
    @Autowired
    private ReportsServiceImpl reportsService;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private CourseRepository courseRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllStudentsByCourseID() {
        Set<Student> studentsMock = new HashSet<>(
                Arrays.asList(TestData.STUDENT1, TestData.STUDENT2, TestData.STUDENT3));
        Course courseMock = new Course(TestData.COURSE1.getId(), TestData.COURSE1.getName());
        courseMock.setStudents(studentsMock);
        Optional<Course> courseQueried = Optional.of(courseMock);
        when(courseRepository.findById(courseMock.getId())).thenReturn(courseQueried);
        ResponseEntity<List<Student>> result = reportsService.getAllStudentsByCourseID(courseMock.getId());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(studentsMock.size(), result.getBody().size());
    }

    @Test
    public void getAllStudentsByCourseIDWithNoFound() {
        Optional<Course> courseQueried = Optional.ofNullable(null);
        when(courseRepository.findById(TestData.NOT_EXISTED_COURSE.getId())).thenReturn(courseQueried);
        ResponseEntity<List<Student>> result = reportsService.getAllStudentsByCourseID(
                TestData.NOT_EXISTED_COURSE.getId());
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void getAllCoursesByStudentID() {
        Set<Course> coursesMock = new HashSet<>(Arrays.asList(
                TestData.COURSE1, TestData.COURSE2, TestData.COURSE3));
        Student studentMock = new Student(
                TestData.STUDENT1.getId(), TestData.STUDENT1.getFirstName(), TestData.STUDENT1.getLastName());
        studentMock.setCourses(coursesMock);
        Optional<Student> studentQueried = Optional.of(studentMock);
        when(studentRepository.findById(studentMock.getId())).thenReturn(studentQueried);
        ResponseEntity<List<Course>> result = reportsService.getAllCoursesByStudentID(studentMock.getId());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(coursesMock.size(), result.getBody().size());
    }

    @Test
    public void getAllCoursesByStudentIDWithNoFound() {
        Optional<Student> studentQueried = Optional.ofNullable(null);
        when(studentRepository.findById(TestData.NOT_EXISTED_STUDENT.getId())).thenReturn(studentQueried);
        ResponseEntity<List<Course>> result = reportsService.getAllCoursesByStudentID(
                TestData.NOT_EXISTED_STUDENT.getId());
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void getAllCoursesWithNoStudents() {
        List<Course> coursesMock = new ArrayList<Course>(
                Arrays.asList(TestData.COURSE1, TestData.COURSE2, TestData.COURSE3));
        when(courseRepository.findAll()).thenReturn(coursesMock);

        ResponseEntity<List<Course>> result = reportsService.getAllCoursesWithNoStudents();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(coursesMock.size(), result.getBody().size());
    }

    @Test
    public void getAllStudentsWithNoCourses() {
        List<Student> studentsMock = new ArrayList<Student>(
                Arrays.asList(TestData.STUDENT1, TestData.STUDENT2, TestData.STUDENT3));
        when(studentRepository.findAll()).thenReturn(studentsMock);

        ResponseEntity<List<Student>> result = reportsService.getAllStudentsWithNoCourses();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(studentsMock.size(), result.getBody().size());
    }
}