package com.metadata.schoolregistrationsystem.controller;

import com.metadata.schoolregistrationsystem.TestData;
import com.metadata.schoolregistrationsystem.entity.Course;
import com.metadata.schoolregistrationsystem.entity.Student;
import com.metadata.schoolregistrationsystem.service.impl.ReportsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ReportsControllerUnitTests {
    ReportsController reportsController;
    ReportsServiceImpl reportsServiceMock;

    @BeforeEach
    public void init() {
        reportsServiceMock = Mockito.mock(ReportsServiceImpl.class);
        reportsController = new ReportsController(reportsServiceMock);
    }

    @Test
    public void getStudentsByCourseID() {
        List<Student> studentsMock = new ArrayList<Student>(
                Arrays.asList(TestData.STUDENT1, TestData.STUDENT2, TestData.STUDENT3));
        ResponseEntity<List<Student>> responseMock = new ResponseEntity<>(studentsMock, HttpStatus.OK);
        Course courseMock = new Course(TestData.COURSE1.getId(), TestData.COURSE1.getName());
        courseMock.setStudents(new HashSet<>(studentsMock));
        when(reportsServiceMock.getAllStudentsByCourseID(courseMock.getId()))
                .thenReturn(responseMock);

        ResponseEntity<List<Student>> result = reportsController.getStudentsByCourseID(courseMock.getId());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(studentsMock.size(), result.getBody().size());
        assertEquals(TestData.STUDENT1.getId(), result.getBody().get(0).getId());
        assertEquals(TestData.STUDENT2.getFirstName(), result.getBody().get(1).getFirstName());
        assertEquals(TestData.STUDENT3.getLastName(), result.getBody().get(2).getLastName());
    }

    @Test
    public void getStudentByCourseIdWithNoFound() {
        ResponseEntity<List<Student>> responseMock = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        when(reportsServiceMock.getAllStudentsByCourseID(600L))
                .thenReturn(responseMock);

        ResponseEntity<List<Student>> result = reportsController.getStudentsByCourseID(600L);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void getCoursesByStudentID() {
        List<Course> coursesMock = new ArrayList<Course>(
                Arrays.asList(TestData.COURSE1, TestData.COURSE2, TestData.COURSE3));
        ResponseEntity<List<Course>> responseMock = new ResponseEntity<>(coursesMock, HttpStatus.OK);
        Student studentMock = new Student(
                TestData.STUDENT1.getId(), TestData.STUDENT1.getFirstName(), TestData.STUDENT1.getLastName());
        studentMock.setCourses(new HashSet<>(coursesMock));

        when(reportsServiceMock.getAllCoursesByStudentID(studentMock.getId()))
                .thenReturn(responseMock);

        ResponseEntity<List<Course>> result = reportsController.getCoursesByStudentID(studentMock.getId());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(coursesMock.size(), result.getBody().size());
        assertEquals(TestData.COURSE1.getId(), result.getBody().get(0).getId());
        assertEquals(TestData.COURSE2.getName(), result.getBody().get(1).getName());
    }

    @Test
    public void getCoursesByStudentIdWithNoFound() {
        ResponseEntity<List<Course>> responseMock = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        when(reportsServiceMock.getAllCoursesByStudentID(500L))
                .thenReturn(responseMock);

        ResponseEntity<List<Course>> result = reportsController.getCoursesByStudentID(500L);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void getCoursesWithNoStudent() {
        List<Course> coursesMock = new ArrayList<Course>(
                Arrays.asList(TestData.COURSE4, TestData.COURSE5, TestData.COURSE6));
        ResponseEntity<List<Course>> responseMock = new ResponseEntity<>(coursesMock, HttpStatus.OK);
        when(reportsServiceMock.getAllCoursesWithNoStudents())
                .thenReturn(responseMock);

        ResponseEntity<List<Course>> result = reportsController.getCoursesWithNoStudent();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(coursesMock.size(), result.getBody().size());
        assertEquals(TestData.COURSE4.getId(), result.getBody().get(0).getId());
        assertEquals(TestData.COURSE5.getName(), result.getBody().get(1).getName());
    }

    @Test
    public void getStudentsWithNoStudent() {
        List<Student> studentsMock = new ArrayList<Student>(
                Arrays.asList(TestData.STUDENT1, TestData.STUDENT2, TestData.STUDENT3));
        ResponseEntity<List<Student>> responseMock = new ResponseEntity<>(studentsMock, HttpStatus.OK);
        when(reportsServiceMock.getAllStudentsWithNoCourses())
                .thenReturn(responseMock);

        ResponseEntity<List<Student>> result = reportsController.getStudentsWithNoCourse();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(studentsMock.size(), result.getBody().size());
        assertEquals(TestData.STUDENT1.getId(), result.getBody().get(0).getId());
        assertEquals(TestData.STUDENT2.getFirstName(), result.getBody().get(1).getFirstName());
        assertEquals(TestData.STUDENT3.getLastName(), result.getBody().get(2).getLastName());
    }
}