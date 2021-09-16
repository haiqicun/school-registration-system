package com.metadata.schoolregistrationsystem.repository;

import com.metadata.schoolregistrationsystem.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
