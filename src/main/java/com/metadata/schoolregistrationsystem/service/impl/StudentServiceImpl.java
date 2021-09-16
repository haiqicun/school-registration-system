package com.metadata.schoolregistrationsystem.service.impl;

import com.metadata.schoolregistrationsystem.entity.Course;
import com.metadata.schoolregistrationsystem.entity.Student;
import com.metadata.schoolregistrationsystem.repository.CourseRepository;
import com.metadata.schoolregistrationsystem.repository.StudentRepository;
import com.metadata.schoolregistrationsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private static final int STUDENT_COURSES_LIMITS = 5;
    private static final int COURSE_STUDENTS_LIMITS = 50;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;


    @Override
    public ResponseEntity<List<Student>> getAllStudents() {
        return new ResponseEntity<>(studentRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Student> getStudentByID(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Student> createStudent(Student student) {
        Student studentSaved = new Student(student.getFirstName(), student.getLastName());
        return new ResponseEntity<>(studentRepository.save(studentSaved), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Student> updateStudent(Student student) {
        Optional<Student> studentQueried = studentRepository.findById(student.getId());
        if (studentQueried.isPresent()) {
            Student studentSaved = studentQueried.get();
            studentSaved.setFirstName(student.getFirstName());
            studentSaved.setLastName(student.getLastName());
            return new ResponseEntity<>(studentRepository.save(studentSaved), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<HttpStatus> deleteStudent(Long id) {
        studentRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Student> registerCourse(Long studentId, Long courseId) {
        Optional<Student> student = studentRepository.findById(studentId);
        Optional<Course> course = courseRepository.findById(courseId);

        if (student.isPresent() && course.isPresent()) {
            Student studentQueried = student.get();
            Course courseQueried = course.get();

            if (studentQueried.getCourses().size() < STUDENT_COURSES_LIMITS
                    && courseQueried.getStudents().size() < COURSE_STUDENTS_LIMITS) {
                studentQueried.getCourses().add(courseQueried);
                return new ResponseEntity<>(studentRepository.save(studentQueried), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
