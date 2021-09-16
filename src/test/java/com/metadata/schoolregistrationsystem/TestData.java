package com.metadata.schoolregistrationsystem;

import com.metadata.schoolregistrationsystem.entity.Course;
import com.metadata.schoolregistrationsystem.entity.Student;

public class TestData {
    public static final Student STUDENT1 = new Student(1L, "F1", "L1");
    public static final Student STUDENT2 = new Student(2L, "F2", "L2");
    public static final Student STUDENT3 = new Student(3L, "F3", "L3");
    public static final Student NOT_EXISTED_STUDENT = new Student(500L, "F500", "L500");
    public static final Course COURSE1 = new Course(1L, "C1");
    public static final Course COURSE2 = new Course(2L, "C2");
    public static final Course COURSE3 = new Course(3L, "C3");
    public static final Course COURSE4 = new Course(4L, "C4");
    public static final Course COURSE5 = new Course(5L, "C5");
    public static final Course COURSE6 = new Course(6L, "C6");
    public static final Course NOT_EXISTED_COURSE = new Course(600L, "C600");
}
