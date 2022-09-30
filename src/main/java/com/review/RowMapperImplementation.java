package com.review;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RowMapperImplementation implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        student.setStudentId(rs.getInt("id"));
        student.setStudentName(rs.getString("studentName"));
        student.setStudentLastName(rs.getString("lastName"));
        student.setFatherName(rs.getString("fatherName"));
        student.setMotherName(rs.getString("motherName"));
        student.setAddress(rs.getString("address"));
        student.setDob(rs.getString("dob"));
        student.setEnglish(rs.getFloat("english"));
        student.setHindi(rs.getFloat("hindi"));
        student.setMaths(rs.getFloat("maths"));
        student.setScience(rs.getFloat("science"));
        student.setSocial(rs.getFloat("social"));
       student.setPercentage(rs.getFloat("percentage"));
        return student;
    }
}
