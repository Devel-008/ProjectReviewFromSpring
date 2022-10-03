package com.review;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PercentageImplement implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        student.setStudentId(rs.getInt("studentId"));
        student.setEnglish(rs.getFloat("english"));
        student.setHindi(rs.getFloat("hindi"));
        student.setMaths(rs.getFloat("maths"));
        student.setScience(rs.getFloat("science"));
        student.setSocial(rs.getFloat("social"));
        return student;
    }
}
