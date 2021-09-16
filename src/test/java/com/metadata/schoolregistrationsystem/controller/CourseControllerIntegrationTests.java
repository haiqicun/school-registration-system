package com.metadata.schoolregistrationsystem.controller;

import com.metadata.schoolregistrationsystem.TestData;
import com.metadata.schoolregistrationsystem.entity.Course;
import com.metadata.schoolregistrationsystem.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
class CourseControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CourseServiceImpl courseServiceMock;

    @Test
    public void listCourses() throws Exception {
        List<Course> coursesMock = new ArrayList<Course>(
                Arrays.asList(TestData.COURSE1, TestData.COURSE2, TestData.COURSE3));
        ResponseEntity<List<Course>> responseMock = new ResponseEntity<>(coursesMock, HttpStatus.OK);

        when(courseServiceMock.getAllCourses())
                .thenReturn(responseMock);
        mockMvc.perform(get("/courses"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getCourseByID() throws Exception {
        ResponseEntity<Course> responseMock = new ResponseEntity<>(TestData.COURSE1, HttpStatus.OK);
        when(courseServiceMock.getCourseByID(TestData.COURSE1.getId()))
                .thenReturn(responseMock);
        mockMvc.perform(get("/courses/"+TestData.COURSE1.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TestData.COURSE1.getId()))
                .andExpect(jsonPath("$.name").value(TestData.COURSE1.getName()));
    }

    @Test
    public void getCourseByIDWithNoFound() throws Exception {
        ResponseEntity<Course> responseMock = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        when(courseServiceMock.getCourseByID(TestData.NOT_EXISTED_COURSE.getId()))
                .thenReturn(responseMock);
        mockMvc.perform(get("/courses/"+TestData.NOT_EXISTED_COURSE.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createCourse() throws Exception {
        ResponseEntity<Course> responseMock = new ResponseEntity<>(TestData.COURSE1, HttpStatus.CREATED);
        when(courseServiceMock.createCourse(any())).thenReturn(responseMock);
        mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"name\":\"C1\"}")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(TestData.COURSE1.getId()))
                .andExpect(jsonPath("$.name").value(TestData.COURSE1.getName()));
    }

    @Test
    public void updateCourse() throws Exception {
        ResponseEntity<Course> responseMock = new ResponseEntity<>(TestData.COURSE2, HttpStatus.OK);
        when(courseServiceMock.updateCourse(any())).thenReturn(responseMock);
        mockMvc.perform(put("/courses")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"name\":\"C2\"}")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TestData.COURSE2.getId()))
                .andExpect(jsonPath("$.name").value(TestData.COURSE2.getName()));
    }

    @Test
    public void deleteCourse() throws Exception {
        ResponseEntity<HttpStatus> responseMock = new ResponseEntity<>(HttpStatus.OK);
        when(courseServiceMock.deleteCourse(TestData.COURSE3.getId())).thenReturn(responseMock);
        mockMvc.perform(delete("/courses/"+TestData.COURSE3.getId()))
                .andExpect(status().isOk());
    }
}