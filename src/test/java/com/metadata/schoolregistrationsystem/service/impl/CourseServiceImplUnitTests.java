package com.metadata.schoolregistrationsystem.service.impl;

import com.metadata.schoolregistrationsystem.TestData;
import com.metadata.schoolregistrationsystem.entity.Course;
import com.metadata.schoolregistrationsystem.repository.CourseRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CourseServiceImplUnitTests {
    @InjectMocks
    @Autowired
    private CourseServiceImpl courseService;
    @Mock
    private CourseRepository courseRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllCourses() {
        List<Course> coursesMock = new ArrayList<Course>(
                Arrays.asList(TestData.COURSE1, TestData.COURSE2, TestData.COURSE3));
        when(courseRepository.findAll()).thenReturn(coursesMock);

        ResponseEntity<List<Course>> result = courseService.getAllCourses();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(coursesMock.size(), result.getBody().size());
    }

    @Test
    public void getCourseByID() {
        Optional<Course> courseMock = Optional.of(TestData.COURSE1);
        when(courseRepository.findById(TestData.COURSE1.getId())).thenReturn(courseMock);
        ResponseEntity<Course> result = courseService.getCourseByID(TestData.COURSE1.getId());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(TestData.COURSE1.getName(), result.getBody().getName());
    }

    @Test
    public void getCourseByIDWithNoFound() {
        Optional<Course> courseMock = Optional.ofNullable(null);
        when(courseRepository.findById(TestData.NOT_EXISTED_COURSE.getId())).thenReturn(courseMock);
        ResponseEntity<Course> result = courseService.getCourseByID(TestData.NOT_EXISTED_COURSE.getId());
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void createCourse() {
        Course curseMock = new Course(TestData.COURSE2.getName());
        when(courseRepository.save(any())).thenReturn(TestData.COURSE2);

        ResponseEntity<Course> result = courseService.createCourse(curseMock);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(TestData.COURSE2.getId(), result.getBody().getId());
        assertEquals(TestData.COURSE2.getName(), result.getBody().getName());
    }

    @Test
    public void updateCourse() {
        Course courseMock = new Course(TestData.COURSE2.getId(), TestData.COURSE2.getName());
        Optional<Course> courseQueried = Optional.of(TestData.COURSE2);
        when(courseRepository.findById(TestData.COURSE2.getId())).thenReturn(courseQueried);
        when(courseRepository.save(any())).thenReturn(TestData.COURSE2);

        ResponseEntity<Course> result = courseService.updateCourse(courseMock);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(TestData.COURSE2.getId(), result.getBody().getId());
        assertEquals(TestData.COURSE2.getName(), result.getBody().getName());
    }

    @Test
    public void updateCourseWithNoFound() {
        Optional<Course> courseQueried = Optional.ofNullable(null);
        when(courseRepository.findById(TestData.NOT_EXISTED_COURSE.getId())).thenReturn(courseQueried);

        ResponseEntity<Course> result = courseService.updateCourse(TestData.NOT_EXISTED_COURSE);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void deleteCourse() {
        ResponseEntity<HttpStatus> result = courseService.deleteCourse(TestData.COURSE1.getId());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}