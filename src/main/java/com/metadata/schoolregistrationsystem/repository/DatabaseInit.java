package com.metadata.schoolregistrationsystem.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
public class DatabaseInit {
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void initDatabase() {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS students;");
            statement.executeUpdate(
                    "CREATE TABLE students("
                            + "id INTEGER Primary key AUTO_INCREMENT, "
                            + "first_name varchar(50) not null, "
                            + "last_name varchar(50) not null)"
            );

            statement.execute("DROP TABLE IF EXISTS courses;");
            statement.executeUpdate(
                    "CREATE TABLE courses("
                            + "id INTEGER Primary key AUTO_INCREMENT, "
                            + "name varchar(50) not null)"
            );

            statement.execute("DROP TABLE IF EXISTS students_enrolled_courses;");
            statement.executeUpdate(
                    "CREATE TABLE students_courses("
                            + "student_id integer not null, "
                            + "course_id integer not null)"
            );

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
