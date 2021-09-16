package com.metadata.schoolregistrationsystem.controller;

import com.metadata.schoolregistrationsystem.TestData;
import com.metadata.schoolregistrationsystem.entity.Course;
import com.metadata.schoolregistrationsystem.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CourseControllerUnitTests {
    CourseController courseController;
    CourseServiceImpl courseServiceMock;

    @BeforeEach
    public void init() {
        courseServiceMock = Mockito.mock(CourseServiceImpl.class);
        courseController = new CourseController(courseServiceMock);
    }

    @Test
    public void listCourses() {
        List<Course> coursesMock = new ArrayList<Course>(
                Arrays.asList(TestData.COURSE1, TestData.COURSE2, TestData.COURSE3));
        ResponseEntity<List<Course>> responseMock = new ResponseEntity<>(coursesMock, HttpStatus.OK);

        when(courseServiceMock.getAllCourses())
                .thenReturn(responseMock);
        ResponseEntity<List<Course>> result = courseController.listCourses();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(coursesMock.size(), result.getBody().size());
        assertEquals(TestData.COURSE1.getName(), result.getBody().get(0).getName());
    }

    @Test
    public void getCourseByID() {
        ResponseEntity<Course> responseMock = new ResponseEntity<>(TestData.COURSE1, HttpStatus.OK);
        when(courseServiceMock.getCourseByID(TestData.COURSE1.getId()))
                .thenReturn(responseMock);

        ResponseEntity<Course> result = courseController.getCourseByID(TestData.COURSE1.getId());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(TestData.COURSE1.getId(), result.getBody().getId());
        assertEquals(TestData.COURSE1.getName(), result.getBody().getName());
    }

    @Test
    public void getCourseByIDWithNoFound() {
        ResponseEntity<Course> responseMock = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        when(courseServiceMock.getCourseByID(600L))
                .thenReturn(responseMock);

        ResponseEntity<Course> result = courseController.getCourseByID(600L);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void createCourse() {
        ResponseEntity<Course> responseMock = new ResponseEntity<>(TestData.COURSE1, HttpStatus.CREATED);
        when(courseServiceMock.createCourse(TestData.COURSE1))
                .thenReturn(responseMock);

        ResponseEntity<Course> result = courseController.createCourse(TestData.COURSE1);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(TestData.COURSE1.getName(), result.getBody().getName());
    }

    @Test
    public void updateCourse() {
        ResponseEntity<Course> responseMock = new ResponseEntity<>(TestData.COURSE4, HttpStatus.OK);
        when(courseServiceMock.updateCourse(TestData.COURSE4))
                .thenReturn(responseMock);

        ResponseEntity<Course> result = courseController.updateCourse(TestData.COURSE4);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(TestData.COURSE4.getId(), result.getBody().getId());
        assertEquals(TestData.COURSE4.getName(), result.getBody().getName());
    }

    @Test
    public void updateCourseWithNoFound() {
        ResponseEntity<Course> responseMock = new ResponseEntity<>(TestData.NOT_EXISTED_COURSE, HttpStatus.NOT_FOUND);
        when(courseServiceMock.updateCourse(TestData.NOT_EXISTED_COURSE))
                .thenReturn(responseMock);

        ResponseEntity<Course> result = courseController.updateCourse(TestData.NOT_EXISTED_COURSE);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void deleteCourse() {
        ResponseEntity<HttpStatus> responseMock = new ResponseEntity<>(HttpStatus.OK);
        when(courseServiceMock.deleteCourse(TestData.COURSE1.getId()))
                .thenReturn(responseMock);

        ResponseEntity<HttpStatus> result = courseController.deleteCourse(TestData.COURSE1.getId());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}