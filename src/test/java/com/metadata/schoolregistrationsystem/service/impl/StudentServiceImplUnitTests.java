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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class StudentServiceImplUnitTests {

    @InjectMocks
    @Autowired
    private StudentServiceImpl studentService;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private CourseRepository courseRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllStudents() {
        List<Student> studentsMock = new ArrayList<Student>(
                Arrays.asList(TestData.STUDENT1, TestData.STUDENT2, TestData.STUDENT3));
        when(studentRepository.findAll()).thenReturn(studentsMock);

        ResponseEntity<List<Student>> result = studentService.getAllStudents();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(studentsMock.size(), result.getBody().size());
    }

    @Test
    public void getStudentByID() {
        Optional<Student> studentMock = Optional.of(TestData.STUDENT1);
        when(studentRepository.findById(TestData.STUDENT1.getId())).thenReturn(studentMock);
        ResponseEntity<Student> result = studentService.getStudentByID(TestData.STUDENT1.getId());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(TestData.STUDENT1.getFirstName(), result.getBody().getFirstName());
        assertEquals(TestData.STUDENT1.getLastName(), result.getBody().getLastName());
    }

    @Test
    public void getStudentByIDWithNoFound() {
        Optional<Student> responseMock = Optional.ofNullable(null);
        when(studentRepository.findById(TestData.NOT_EXISTED_STUDENT.getId())).thenReturn(responseMock);
        ResponseEntity<Student> result = studentService.getStudentByID(TestData.NOT_EXISTED_STUDENT.getId());
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void createStudent() {
        Student studentMock = new Student(TestData.STUDENT2.getFirstName(), TestData.STUDENT2.getLastName());
        when(studentRepository.save(any())).thenReturn(TestData.STUDENT2);

        ResponseEntity<Student> result = studentService.createStudent(studentMock);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(TestData.STUDENT2.getId(), result.getBody().getId());
        assertEquals(TestData.STUDENT2.getFirstName(), result.getBody().getFirstName());
        assertEquals(TestData.STUDENT2.getLastName(), result.getBody().getLastName());
    }

    @Test
    public void updateStudent() {
        Student studentMock = new Student(
                TestData.STUDENT2.getId(),TestData.STUDENT2.getFirstName(), TestData.STUDENT2.getLastName());
        Optional<Student> studentQueried = Optional.of(TestData.STUDENT2);
        when(studentRepository.findById(TestData.STUDENT2.getId())).thenReturn(studentQueried);
        when(studentRepository.save(any())).thenReturn(TestData.STUDENT2);

        ResponseEntity<Student> result = studentService.updateStudent(studentMock);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(TestData.STUDENT2.getId(), result.getBody().getId());
        assertEquals(TestData.STUDENT2.getFirstName(), result.getBody().getFirstName());
        assertEquals(TestData.STUDENT2.getLastName(), result.getBody().getLastName());
    }

    @Test
    public void updateStudentWithNoFound() {
        Optional<Student> studentQueried = Optional.ofNullable(null);
        when(studentRepository.findById(TestData.NOT_EXISTED_STUDENT.getId())).thenReturn(studentQueried);

        ResponseEntity<Student> result = studentService.updateStudent(TestData.NOT_EXISTED_STUDENT);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void deleteStudent() {
        ResponseEntity<HttpStatus> result = studentService.deleteStudent(TestData.STUDENT1.getId());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void registerCourse() {
        Student studentMock = new Student(
                TestData.STUDENT1.getId(), TestData.STUDENT1.getFirstName(), TestData.STUDENT1.getLastName());

        Set<Course> courseSet = new HashSet<>(Arrays.asList(TestData.COURSE1));
        studentMock.setCourses(courseSet);
        Optional<Student> studentQueried = Optional.of(studentMock);
        Optional<Course> courseQueried = Optional.of(TestData.COURSE1);
        when(studentRepository.findById(studentMock.getId())).thenReturn(studentQueried);
        when(courseRepository.findById(TestData.COURSE1.getId())).thenReturn(courseQueried);
        when(studentRepository.save(any())).thenReturn(studentMock);

        ResponseEntity<Student> result = studentService.registerCourse(studentMock.getId(), TestData.COURSE1.getId());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(studentMock.getId(), result.getBody().getId());
        assertEquals(studentMock.getFirstName(), result.getBody().getFirstName());
        assertEquals(studentMock.getLastName(), result.getBody().getLastName());
        assertEquals(courseSet.size(), result.getBody().getCourses().size());
    }
}