package com.metadata.schoolregistrationsystem.controller;

import com.metadata.schoolregistrationsystem.TestData;
import com.metadata.schoolregistrationsystem.entity.Course;
import com.metadata.schoolregistrationsystem.entity.Student;
import com.metadata.schoolregistrationsystem.service.impl.ReportsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportsController.class)
class ReportsControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReportsServiceImpl reportsServiceMock;

    @Test
    public void getStudentsByCourseID() throws Exception {
        List<Student> studentsMock = new ArrayList<Student>(
                Arrays.asList(TestData.STUDENT1, TestData.STUDENT2, TestData.STUDENT3));
        ResponseEntity<List<Student>> responseMock = new ResponseEntity<>(studentsMock, HttpStatus.OK);
        when(reportsServiceMock.getAllStudentsByCourseID(TestData.COURSE1.getId()))
                .thenReturn(responseMock);
        mockMvc.perform(get("/reports/getStudentsByCourseId/"+TestData.COURSE1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void getCoursesByStudentID() throws Exception {
        List<Course> coursesMock = new ArrayList<Course>(
                Arrays.asList(TestData.COURSE1, TestData.COURSE2, TestData.COURSE3));
        ResponseEntity<List<Course>> responseMock = new ResponseEntity<>(coursesMock, HttpStatus.OK);
        when(reportsServiceMock.getAllCoursesByStudentID(TestData.STUDENT1.getId()))
                .thenReturn(responseMock);
        mockMvc.perform(get("/reports/getCoursesByStudentId/"+TestData.STUDENT1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void getCoursesWithNoStudent() throws Exception {
        List<Course> coursesMock = new ArrayList<Course>(
                Arrays.asList(TestData.COURSE1, TestData.COURSE2, TestData.COURSE3));
        ResponseEntity<List<Course>> responseMock = new ResponseEntity<>(coursesMock, HttpStatus.OK);
        when(reportsServiceMock.getAllCoursesWithNoStudents()).thenReturn(responseMock);
        mockMvc.perform(get("/reports/getCoursesWithNoStudent"))
                .andExpect(status().isOk());
    }

    @Test
    public void getStudentsWithNoCourse() throws Exception {
        List<Student> studentsMock = new ArrayList<Student>(
                Arrays.asList(TestData.STUDENT1, TestData.STUDENT2, TestData.STUDENT3));
        ResponseEntity<List<Student>> responseMock = new ResponseEntity<>(studentsMock, HttpStatus.OK);
        when(reportsServiceMock.getAllStudentsWithNoCourses())
                .thenReturn(responseMock);
        mockMvc.perform(get("/reports/getStudentsWithNoCourse"))
                .andExpect(status().isOk());
    }
}