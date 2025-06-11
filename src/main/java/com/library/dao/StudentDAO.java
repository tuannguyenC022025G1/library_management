package com.library.dao;

import com.library.model.Student;
import com.library.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Student";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getString("student_id"));
                student.setFullName(rs.getString("full_name"));
                student.setClassName(rs.getString("class_name"));
                students.add(student);
            }
        }
        return students;
    }
}