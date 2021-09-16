package com.metadata.schoolregistrationsystem.controller;

import com.metadata.schoolregistrationsystem.TestData;
import com.metadata.schoolregistrationsystem.entity.Student;
import com.metadata.schoolregistrationsystem.service.impl.StudentServiceImpl;
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
import java.util.HashSet;
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

@WebMvcTest(StudentController.class)
class StudentControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentServiceImpl studentServiceMock;


    @Test
    public void listStudents() throws Exception {
        List<Student> studentsMock = new ArrayList<Student>(
                Arrays.asList(TestData.STUDENT1, TestData.STUDENT2, TestData.STUDENT3));
        ResponseEntity<List<Student>> responseMock = new ResponseEntity<>(studentsMock, HttpStatus.OK);

        when(studentServiceMock.getAllStudents())
                .thenReturn(responseMock);
        mockMvc.perform(get("/students"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getStudentByID() throws Exception {
        ResponseEntity<Student> responseMock = new ResponseEntity<>(TestData.STUDENT1, HttpStatus.OK);
        when(studentServiceMock.getStudentByID(TestData.STUDENT1.getId()))
                .thenReturn(responseMock);
        mockMvc.perform(get("/students/"+TestData.STUDENT1.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TestData.STUDENT1.getId()))
                .andExpect(jsonPath("$.firstName").value(TestData.STUDENT1.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(TestData.STUDENT1.getLastName()));
    }

    @Test
    public void getStudentByIDWithNoFound() throws Exception {
        ResponseEntity<Student> responseMock = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        when(studentServiceMock.getStudentByID(TestData.NOT_EXISTED_STUDENT.getId()))
                .thenReturn(responseMock);
        mockMvc.perform(get("/students/"+TestData.NOT_EXISTED_STUDENT.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createStudent() throws Exception {
        ResponseEntity<Student> responseMock = new ResponseEntity<>(TestData.STUDENT1, HttpStatus.CREATED);
        when(studentServiceMock.createStudent(any())).thenReturn(responseMock);
        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"firstName\":\"F1\",\"lastName\":\"L1\"}")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(TestData.STUDENT1.getId()))
                .andExpect(jsonPath("$.firstName").value(TestData.STUDENT1.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(TestData.STUDENT1.getLastName()));
    }

    @Test
    public void updateStudent() throws Exception {
        ResponseEntity<Student> responseMock = new ResponseEntity<>(TestData.STUDENT2, HttpStatus.OK);
        when(studentServiceMock.updateStudent(any())).thenReturn(responseMock);
        mockMvc.perform(put("/students")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"firstName\":\"F2\",\"lastName\":\"L2\"}")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TestData.STUDENT2.getId()))
                .andExpect(jsonPath("$.firstName").value(TestData.STUDENT2.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(TestData.STUDENT2.getLastName()));
    }

    @Test
    public void deleteStudent() throws Exception {
        ResponseEntity<HttpStatus> responseMock = new ResponseEntity<>(HttpStatus.OK);
        when(studentServiceMock.deleteStudent(TestData.STUDENT3.getId())).thenReturn(responseMock);
        mockMvc.perform(delete("/students/"+TestData.STUDENT3.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void registerCourse() throws Exception {
        Student studentMock = new Student(
                TestData.STUDENT1.getId(), TestData.STUDENT1.getFirstName(), TestData.STUDENT1.getLastName());
        studentMock.setCourses(new HashSet<>(Arrays.asList(TestData.COURSE1)));
        ResponseEntity<Student> responseMock= new ResponseEntity<>(studentMock, HttpStatus.OK);
        when(studentServiceMock.registerCourse(studentMock.getId(), TestData.COURSE1.getId()))
                .thenReturn(responseMock);
        String postUrl = "/students/"+studentMock.getId()+"/register/"+TestData.COURSE1.getId();
        mockMvc.perform(post(postUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentMock.getId()))
                .andExpect(jsonPath("$.firstName").value(studentMock.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(studentMock.getLastName()));
    }
}