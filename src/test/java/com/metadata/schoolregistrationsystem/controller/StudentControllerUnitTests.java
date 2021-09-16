package com.metadata.schoolregistrationsystem.controller;

import com.metadata.schoolregistrationsystem.TestData;
import com.metadata.schoolregistrationsystem.entity.Course;
import com.metadata.schoolregistrationsystem.entity.Student;
import com.metadata.schoolregistrationsystem.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class StudentControllerUnitTests {

    StudentController studentController;
    StudentServiceImpl studentServiceMock;

    @BeforeEach
    public void init() {
        studentServiceMock = Mockito.mock(StudentServiceImpl.class);
        studentController = new StudentController(studentServiceMock);
    }

    @Test
    public void listStudents() {
        List<Student> studentsMock = new ArrayList<Student>(
                Arrays.asList(TestData.STUDENT1, TestData.STUDENT2, TestData.STUDENT3));
        ResponseEntity<List<Student>> responseMock = new ResponseEntity<>(studentsMock, HttpStatus.OK);

        when(studentServiceMock.getAllStudents())
                .thenReturn(responseMock);
        ResponseEntity<List<Student>> result = studentController.listStudents();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(studentsMock.size(), result.getBody().size());
        assertEquals(TestData.STUDENT1.getFirstName(), result.getBody().get(0).getFirstName());
        assertEquals(TestData.STUDENT2.getLastName(), result.getBody().get(1).getLastName());
    }

    @Test
    public void getStudentByID() {
        ResponseEntity<Student> responseMock = new ResponseEntity<>(TestData.STUDENT1, HttpStatus.OK);
        when(studentServiceMock.getStudentByID(TestData.STUDENT1.getId()))
                .thenReturn(responseMock);
        ResponseEntity<Student> result = studentController.getStudentByID(TestData.STUDENT1.getId());

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(TestData.STUDENT1.getId(), result.getBody().getId());
        assertEquals(TestData.STUDENT1.getFirstName(), result.getBody().getFirstName());
        assertEquals(TestData.STUDENT1.getLastName(), result.getBody().getLastName());
    }

    @Test
    public void getStudentByIDWithNoFound() {
        ResponseEntity<Student> responseMock = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        when(studentServiceMock.getStudentByID(500L))
                .thenReturn(responseMock);

        ResponseEntity<Student> result = studentController.getStudentByID(500L);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void createStudent() {
        ResponseEntity<Student> responseMock = new ResponseEntity<>(TestData.STUDENT1, HttpStatus.CREATED);
        when(studentServiceMock.createStudent(TestData.STUDENT1))
                .thenReturn(responseMock);

        ResponseEntity<Student> result = studentController.CreateStudent(TestData.STUDENT1);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(TestData.STUDENT1.getFirstName(), result.getBody().getFirstName());
        assertEquals(TestData.STUDENT1.getLastName(), result.getBody().getLastName());
    }

    @Test
    public void updateStudent() {
        ResponseEntity<Student> responseMock = new ResponseEntity<>(TestData.STUDENT2, HttpStatus.OK);
        when(studentServiceMock.updateStudent(TestData.STUDENT2))
                .thenReturn(responseMock);

        ResponseEntity<Student> result = studentController.updateStudent(TestData.STUDENT2);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(TestData.STUDENT2.getFirstName(), result.getBody().getFirstName());
        assertEquals(TestData.STUDENT2.getLastName(), result.getBody().getLastName());
    }

    @Test
    public void updateStudentWithNoFound() {
        ResponseEntity<Student> responseMock = new ResponseEntity<>(TestData.NOT_EXISTED_STUDENT, HttpStatus.NOT_FOUND);
        when(studentServiceMock.updateStudent(TestData.NOT_EXISTED_STUDENT))
                .thenReturn(responseMock);

        ResponseEntity<Student> result = studentController.updateStudent(TestData.NOT_EXISTED_STUDENT);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void deleteStudent() {
        ResponseEntity<HttpStatus> responseMock = new ResponseEntity<>(HttpStatus.OK);
        when(studentServiceMock.deleteStudent(TestData.STUDENT1.getId()))
                .thenReturn(responseMock);

        ResponseEntity<HttpStatus> result = studentController.deleteStudent(TestData.STUDENT1.getId());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void registerCourse() {
        Set<Course> courses = new HashSet<>
                (Arrays.asList(TestData.COURSE1, TestData.COURSE2, TestData.COURSE3));
        Student studentMock = new Student(
                TestData.STUDENT1.getId(), TestData.STUDENT1.getFirstName(), TestData.STUDENT1.getLastName());
        studentMock.setCourses(courses);
        ResponseEntity<Student> responseMock = new ResponseEntity<>(studentMock, HttpStatus.OK);
        when(studentServiceMock.registerCourse(studentMock.getId(), TestData.COURSE3.getId()))
                .thenReturn(responseMock);

        ResponseEntity<Student> result = studentController.registerCourse(
                studentMock.getId(), TestData.COURSE3.getId());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(studentMock.getId(), result.getBody().getId());
        assertEquals(courses.size(), result.getBody().getCourses().size());
    }
}