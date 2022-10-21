package com.review;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class StudentDaoImplement implements StudentDao {
    private JdbcTemplate jdbcTemplate;
    String query;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void setInsertRecords(Student student, Scanner sc, Logger logger) {
        try {
            logger.info("Logger in SetInsertRecords");
            //input student-id
            System.out.print("Enter Student-Id:=");
            student.setStudentId(sc.nextInt());
            query = "select * from student where id = ?";
            RowMapper<Student> rowMapper = new IdCheck();
            Student student1 = this.jdbcTemplate.queryForObject(query,rowMapper,student.getStudentId());

            if(student.getStudentId() != student1.getStudentId()) {
                logger.info("For ID {}", student.getStudentId());
                //input-studentName
                System.out.print("Enter Student-Name:= (Example: Pratibha)");
                student.setStudentName(sc.next());
                if (!student.getStudentName().matches("[A-Za-z]*")) {
                    System.out.print("Incorrect format");
                }
                //input student LastName
                System.out.print("Enter Student-Last Name:- (Example: Soni");
                student.setStudentLastName(sc.next());
                if (!student.getStudentLastName().matches("[A-Za-z]*")) {
                    System.out.print("Incorrect format");
                }
                //input student-FatherName
                System.out.print("Enter Student's Father-Name:= (Example: Kishan)");
                student.setFatherName(sc.next());
                if (!student.getFatherName().matches("[A-Za-z]*")) {
                    System.out.print("Incorrect format");
                }
                //input student motherName
                System.out.print("Enter Student's Mother-Name:= (Example: Sharmila)");
                student.setMotherName(sc.next());
                if (!student.getMotherName().matches("[A-Za-z]*")) {
                    System.out.print("Incorrect format");
                }
                //input student Address
                System.out.print("Enter Student's Address:= (Example: Bikaner)");
                student.setAddress(sc.next());
                if (!student.getAddress().matches("[A-Za-z][A-Za-z0-9]*")) {
                    System.out.print("Incorrect format");
                }
                //input student date of birth
                System.out.print("Enter Student's DOB yyyy-mm-dd:= (Example: 2000-10-10 )");
                student.setDob(sc.next());
                if (!student.getDob().matches("[1-2][0-9][0-9][0-9][-][0-1][0-9][-][0-3][0-9]")) {
                    System.out.print("Date has incorrect format");
                }
                //input english marks
                System.out.print("Enter English-Marks out of 100:=");
                student.setEnglish(sc.nextFloat());
                if (student.getEnglish() > 100) {
                    System.out.print("Marks entered must be more than 100");
                }
                //input hindi marks
                System.out.print("Enter Hindi-Marks out of 100:=");
                student.setHindi(sc.nextFloat());
                if (student.getHindi() > 100) {
                    System.out.print("Marks entered must be more than 100");
                }
                //input maths marks
                System.out.print("Enter Maths-Marks out of 100:=");
                student.setMaths(sc.nextFloat());
                if (student.getMaths() > 100) {
                    System.out.print("Marks entered must be more than 100");
                }
                //input science marks
                System.out.print("Enter Science-Marks out of 100:=");
                student.setScience(sc.nextFloat());
                if (student.getScience() > 100) {
                    System.out.print("Marks entered must be more than 100");
                }
                //input social marks
                System.out.print("Enter Social-Marks out of 100:=");
                student.setSocial(sc.nextFloat());
                if (student.getSocial() > 100) {
                    System.out.print("Marks entered must be more than 100");
                }
                //percentage
                float total = student.getEnglish() + student.getHindi() + student.getMaths() + student.getScience() + student.getSocial();
                student.setPercentage((total * 100) / 500);
                if(insertRecords(student,logger)){
                    System.out.println("Inserted data for ID: "+student.getStudentId());
                }
            }else {
                System.out.println("Already Exist");
                throw new IllegalArgumentException("Data Already Exist for ID " + student.getStudentId());
            }
        } catch (InputMismatchException | NullPointerException  | IllegalArgumentException e) {
            logger.error("Error at insert := ", e);
        }
       // insertRecords(student, logger);
    }

    @Override
    public boolean insertRecords(Student student, Logger logger) {

        String insertStudent = "insert into student (id, studentName,lastName) values(?, ?, ?)";
        String insertStudentMarks = "insert into studentMarks(studentId, english, hindi, maths, science, social, percentage)" +
                "values(?, ?, ?, ?, ?, ?, ?)";
        String insertStudentDetails = "insert into studentPersonalDetails(studentId, fatherName, motherName, address," +
                "dob) values(?, ?, ?, ?, ?)";

        int i = this.jdbcTemplate.update(insertStudent, student.getStudentId(), student.getStudentName(), student.getStudentLastName());
        int i1 = this.jdbcTemplate.update(insertStudentDetails, student.getStudentId(), student.getFatherName(), student.getMotherName(), student.getAddress(), student.getDob
                ());
        int i2 = this.jdbcTemplate.update(insertStudentMarks, student.getStudentId(), student.getEnglish(), student.getHindi(), student.getMaths(), student.getScience(),
                student.getSocial(), student.getPercentage());

        if (i > 0 && i1 > 0 && i2 > 0) {
            logger.info("Inserted ID {} Data",student.getStudentId());
            return true;
        } else {
            System.out.print("Not Inserted!!");
            logger.warn("ID {} Data not-Inserted",student.getStudentId());
            return false;
        }
    }

    @Override
    public void setDeleteRecords(Student student, Scanner sc, Logger logger) {
        logger.info("Logger in SetDelete");
        System.out.print("Enter the ID of student whose data you want too delete :-");
        student.setStudentId(sc.nextInt());
        logger.info("Data Deleted for {} ",student.getStudentId());
        //deleteRecords(student, logger);
        if(deleteRecords(student,logger)){
            System.out.println("Deleted!!!");
        }
    }

    @Override
    public boolean deleteRecords(Student student, Logger logger) {
        String deleteStudent = "delete from student where id = ?";
        String deleteStudentMarks = "delete from studentMarks where studentId = ?";
        String deleteStudentDetails = "delete from studentPersonalDetails where studentId = ?";
        int i = this.jdbcTemplate.update(deleteStudent, student.getStudentId());
        int i1 = this.jdbcTemplate.update(deleteStudentDetails, student.getStudentId());
        int i2 = this.jdbcTemplate.update(deleteStudentMarks, student.getStudentId());
        if (i > 0 && i1 > 0 && i2 > 0) {
            logger.info("ID {} Data deleted",student.getStudentId());
            return true;
        } else {
            System.out.print("not deleted!!");
            logger.warn("ID {} Data not-deleted",student.getStudentId());
            return false;
        }
    }

    @Override
    public boolean updateRecords(Student student, Logger logger) {
        if (updateName(student, logger) && updateLName(student, logger) && updateFName(student, logger) &&
                updateMName(student, logger) && updateAddress(student, logger) && updateDob(student, logger)
                && updateEnglish(student, logger) && updateHindi(student, logger) && updateMaths(student, logger)
                && updateScience(student, logger) && updateSocial(student, logger) && updatePercentage(student, logger)) {
            System.out.print("Updated All!!");
            return true;
        } else {
            System.out.print("Not Updated!!");
            return false;
        }
    }

    @Override
    public void setUpdateRecords(Student student, Scanner sc, Logger logger) {
        logger.info("Logger init to Set Update Records!!");
        try {
            int i;
            System.out.print("Enter the ID of student whose data you want Update :=");
            student.setStudentId(sc.nextInt());
            logger.info("Update in ID {}",student.getStudentId());

            query = "select * from student where id = ?";
            RowMapper<Student> rowMapper = new IdCheck();
            Student student1 = this.jdbcTemplate.queryForObject(query,rowMapper,student.getStudentId());

            if(student.getStudentId() == student1.getStudentId()) {

                System.out.print("Press 1 to Update Name || Press 2 to Personal details || Press 3 to Update Marks :=");
                int choice = sc.nextInt();
                if (choice == 1) {
                    System.out.print("Press 1 to update StudentName || Press 2 to update LastName ");
                    i = sc.nextInt();
                    //For Name
                    switch (i) {
                        case 1 -> {
                            try {
                                System.out.print("Update Name:=");
                                student.setStudentName(sc.next());
                                if (!student.getStudentName().matches("[A-Za-z]*")) {
                                    System.out.print("Incorrect format  ");
                                    logger.warn("Incorrect format for UpdateName for ID {}", student.getStudentId());
                                }
                                updateName(student, logger);
                            } catch (InputMismatchException | NullPointerException e) {
                                logger.error("Error on line 211 of SetUpdate :- ", e);
                            }
                        }
                        case 2 -> {
                            try {
                                System.out.print("Update Last-Name:=");
                                student.setStudentLastName(sc.next());
                                if (!student.getStudentLastName().matches("[A-Za-z]*")) {
                                    System.out.print("Incorrect format");
                                    logger.warn("Incorrect format for UpdateLastName for ID {}", student.getStudentId());
                                }
                                updateLName(student, logger);
                            } catch (InputMismatchException | NullPointerException e) {
                                logger.error("Error on line 224 of SetUpdate:- ", e);
                            }
                        }
                        default -> System.out.print("Wrong Choice");
                    }
                } else if (choice == 2) {
                    System.out.print("Press 1 to update FatherName || Press 2 to update MotherName || Press 3 to update Address || Press 4 to update DOB ");
                    i = sc.nextInt();
                    switch (i) {
                        case 1 -> {
                            try {
                                System.out.print("Update FatherName:=");
                                student.setFatherName(sc.next());
                                if (!student.getFatherName().matches("[A-Za-z]*")) {
                                    System.out.print("Incorrect format");
                                    logger.warn("Incorrect format for UpdateFatherName for ID {}", student.getStudentId());
                                }
                                updateFName(student, logger);
                            } catch (InputMismatchException | NullPointerException e) {
                                logger.error("Error on line 245 in SetUpdate :- ", e);
                            }
                        }
                        case 2 -> {
                            try {
                                System.out.print("Update Mother-Name:=");
                                student.setMotherName(sc.next());
                                if (!student.getMotherName().matches("[A-Za-z]*")) {
                                    System.out.print("Incorrect format");
                                    logger.warn("Incorrect format for UpdateMotherName for ID {}", student.getStudentId());
                                }
                                updateMName(student, logger);
                            } catch (InputMismatchException | NullPointerException e) {
                                logger.error("Error on line 258 in SetUpdate:- ", e);
                            }
                        }
                        case 3 -> {
                            try {
                                System.out.print("Update Address:=");
                                student.setAddress(sc.next());
                                if (!student.getAddress().matches("[A-Za-z][A-Za-z0-9]*")) {
                                    System.out.print("Incorrect format");
                                    logger.warn("Incorrect format for UpdateAddress for ID {}", student.getStudentId());
                                }
                                updateAddress(student, logger);
                            } catch (InputMismatchException | NullPointerException e) {
                                logger.error("Error on line 271 in SetUpdate:- ", e);
                            }
                        }
                        case 4 -> {
                            try {
                                System.out.print("Update DOB:=");
                                student.setDob(sc.next());
                                if (!student.getDob().matches("[1-2][0-9][0-9][0-9][-][0-1][0-9][-][0-3][0-9]")) {
                                    System.out.print("Incorrect format");
                                    logger.warn("Incorrect format for UpdateDOB for ID {}", student.getStudentId());
                                }
                                updateDob(student, logger);
                            } catch (InputMismatchException | NullPointerException e) {
                                logger.error("Error on line 284 in SetUpdate:- ", e);
                            }
                        }
                        default -> System.out.print("Wrong Choice");
                    }
                } else if (choice == 3) {
                    System.out.print("Press 1 to update English || Press 2 to update Hindi || Press 3 to update Maths || Press 4 to update Science || Press 5 to update Social:= ");
                    i = sc.nextInt();
                    switch (i) {
                        case 1 -> {
                            try {
                                System.out.print("Update English Marks:=");
                                student.setEnglish(sc.nextFloat());
                                if (student.getEnglish() > 100) {
                                    System.out.print("Marks entered must be more than 100");
                                    logger.warn("Incorrect format for UpdateEnglishMarks for ID {}", student.getStudentId());
                                }
                                updateEnglish(student, logger);
                                updatePercentage(student, logger);
                            } catch (InputMismatchException | NullPointerException e) {
                                logger.error("Error on line 306 in update:- ", e);
                            }
                        }
                        case 2 -> {
                            try {
                                System.out.print("Update Hindi Marks:=");
                                student.setHindi(sc.nextFloat());
                                if (student.getHindi() > 100) {
                                    System.out.print("Marks entered must be more than 100");
                                    logger.warn("Incorrect format for UpdateHindiMarks for ID {}", student.getStudentId());
                                }
                                updateHindi(student, logger);
                                updatePercentage(student, logger);
                            } catch (InputMismatchException | NullPointerException e) {
                                logger.error("Error on line 320 in SetUpdate:- ", e);
                            }
                        }
                        case 3 -> {
                            try {
                                System.out.print("Update Maths Marks:=");
                                student.setMaths(sc.nextFloat());
                                if (student.getMaths() > 100) {
                                    System.out.print("Marks entered must be more than 100");
                                    logger.warn("Incorrect format for UpdateMathsMarks for ID {}", student.getStudentId());
                                }
                                updateMaths(student, logger);
                                updatePercentage(student, logger);
                            } catch (InputMismatchException | NullPointerException e) {
                                logger.error("Error on line 334 in SetUpdate:- ", e);
                            }
                        }
                        case 4 -> {
                            try {
                                System.out.print("Update Science Marks:=");
                                student.setScience(sc.nextFloat());
                                if (student.getScience() > 100) {
                                    System.out.print("Marks entered must be more than 100");
                                    logger.warn("Incorrect format for UpdateScienceMarks for ID {}", student.getStudentId());
                                }
                                updateScience(student, logger);
                                updatePercentage(student, logger);
                            } catch (InputMismatchException | NullPointerException e) {
                                logger.error("Error on line 348 in SetUpdate:- ", e);
                            }
                        }
                        case 5 -> {
                            try {
                                System.out.print("Update Social Marks:=");
                                student.setSocial(sc.nextFloat());
                                if (student.getSocial() > 100) {
                                    System.out.print("Marks entered must be more than 100");
                                    logger.warn("Incorrect format for UpdateSocialMarks for ID {}", student.getStudentId());
                                }
                                updateSocial(student, logger);
                                updatePercentage(student, logger);
                            } catch (InputMismatchException | NullPointerException e) {
                                logger.error("Error on line 362 in update:- ", e);
                            }
                        }
                        default -> System.out.print("Wrong Choice");
                    }
                } else {
                    System.out.print("Wrong Choice!!");
                }
            }else {
                System.out.println("Already Exist");
                throw new IllegalArgumentException("Data Already Exist for ID " + student.getStudentId());
            }
        } catch (InputMismatchException | NullPointerException e) {
            logger.error("Error at selectRecord line 371", e);
        }
    }

    @Override
    public boolean updateName(Student student, Logger logger) {
        query = "update student set studentName = ? where id = ?";
        int i = this.jdbcTemplate.update(query, student.getStudentName(), student.getStudentId());
        if (i > 0) {
            return true;
        } else {
            System.out.print("Not Updated Name");
            logger.warn("Not Updated Name");
            return false;
        }
    }

    @Override
    public boolean updateLName(Student student, Logger logger) {
        query = "update student set lastName = ? where id = ?";
        int i = this.jdbcTemplate.update(query, student.getStudentLastName(), student.getStudentId());
        if (i > 0) {
            return true;
        } else {
            System.out.print("Not Updated LastName");
            logger.warn("Not Updated LastName");
            return false;
        }
    }

    @Override
    public boolean updateFName(Student student, Logger logger) {
        query = "update studentPersonalDetails set fatherName = ? where studentId = ?";
        int i = this.jdbcTemplate.update(query, student.getFatherName(), student.getStudentId());
        if (i > 0) {
            return true;
        } else {
            System.out.print("Not Updated FatherName");
            logger.warn("Not Updated FatherName");
            return false;
        }
    }

    @Override
    public boolean updateMName(Student student, Logger logger) {
        query = "update studentPersonalDetails set motherName = ? where studentId = ?";
        int i = this.jdbcTemplate.update(query, student.getMotherName(), student.getStudentId());
        if (i > 0) {
            return true;
        } else {
            System.out.print("Not Updated MotherName");
            logger.warn("Not Updated MotherName");
            return false;
        }
    }

    @Override
    public boolean updateAddress(Student student, Logger logger) {
        query = "update studentPersonalDetails set address = ? where studentId = ?";
        int i = this.jdbcTemplate.update(query, student.getAddress(), student.getStudentId());
        if (i > 0) {
            return true;
        } else {
            System.out.print("Not Updated Address");
            logger.warn("Not Updated Address");
            return false;
        }
    }

    @Override
    public boolean updateDob(Student student, Logger logger) {
        query = "update studentPersonalDetails set dob = ? where studentId = ?";
        int i = this.jdbcTemplate.update(query, student.getDob(), student.getStudentId());
        if (i > 0) {
            return true;
        } else {
            System.out.print("Not Updated DOB");
            logger.warn("Not Updated DOB");
            return false;
        }
    }

    @Override
    public boolean updateEnglish(Student student, Logger logger) {
        query = "update studentMarks set english = ? where studentId = ?";
        int i = this.jdbcTemplate.update(query, student.getEnglish(), student.getStudentId());
        if (i > 0) {
            return true;
        } else {
            System.out.print("Not Updated English");
            logger.warn("Not Updated English");
            return false;
        }
    }

    @Override
    public boolean updateHindi(Student student, Logger logger) {
        query = "update studentMarks set hindi = ? where studentId = ?";
        int i = this.jdbcTemplate.update(query, student.getHindi(), student.getStudentId());
        if (i > 0) {
            return true;
        } else {
            System.out.print("Not Updated Hindi");
            logger.warn("Not Updated Hindi");
            return false;
        }
    }

    @Override
    public boolean updateMaths(Student student, Logger logger) {
        query = "update studentMarks set maths = ? where studentId = ?";
        int i = this.jdbcTemplate.update(query, student.getMaths(), student.getStudentId());
        if (i > 0) {
            return true;
        } else {
            System.out.print("Not Updated Maths");
            logger.warn("Not Updated Maths");
            return false;
        }
    }

    @Override
    public boolean updateScience(Student student, Logger logger) {
        query = "update studentMarks set science = ? where studentId = ?";
        int i = this.jdbcTemplate.update(query, student.getScience(), student.getStudentId());
        if (i > 0) {
            return true;
        } else {
            System.out.print("Not Updated Science");
            logger.warn("Not Updated Science");
            return false;
        }
    }

    @Override
    public boolean updateSocial(Student student, Logger logger) {
        query = "update studentMarks set social = ? where studentId = ?";
        int i = this.jdbcTemplate.update(query, student.getSocial(), student.getStudentId());
        if (i > 0) {
            return true;
        } else {
            System.out.print("Not Updated Social");
            logger.warn("Not Updated Social");
            return false;
        }
    }

    @Override
    public boolean updatePercentage(Student student, Logger logger) {
        query = "select * from studentMarks where studentId = ?";
        RowMapper<Student> rowMapper = new PercentageImplement();
        Student student1 = this.jdbcTemplate.queryForObject(query,rowMapper,student.getStudentId());
        float total = student1.getEnglish() + student1.getHindi() + student1.getMaths() + student1.getScience()+ student1.getSocial();
        student.setPercentage((total * 100) / 500);
        String update = "update studentMarks set percentage = ? where studentId = ?";
        int i = this.jdbcTemplate.update(update, student.getPercentage(), student.getStudentId());
        if (i > 0) {
            return true;
        } else {
            System.out.print("Not Updated Percentage");
            logger.warn("Not Updated Percentage");
            return false;
        }
    }


    @Override
    public Student fetchRecords(Student student, Logger logger) {
        query = " select student.id, student.studentName, student.lastName,  studentPersonalDetails.fatherName, " +
                "studentPersonalDetails.motherName, studentPersonalDetails.address, studentPersonalDetails.dob, " +
                "studentMarks.english, studentMarks.hindi, studentMarks.maths, studentMarks.science, studentMarks.social" +
                ", studentMarks.percentage  from student join studentMarks on student.id = studentMarks.studentId join " +
                "studentPersonalDetails on studentPersonalDetails.studentId = student.id where student.id = ? ";
        RowMapper<Student> rowMapper = new RowMapperImplementation();
        Student student1 = this.jdbcTemplate.queryForObject(query,rowMapper,student.getStudentId());
        return student1;
    }
    @Override
    public List<Student> getAllStudents() {
        query = " select student.id, student.studentName, student.lastName,  studentPersonalDetails.fatherName, " +
                "studentPersonalDetails.motherName, studentPersonalDetails.address, studentPersonalDetails.dob, " +
                "studentMarks.english, studentMarks.hindi, studentMarks.maths, studentMarks.science, studentMarks.social" +
                ", studentMarks.percentage  from student join studentMarks on student.id = studentMarks.studentId join " +
                "studentPersonalDetails on studentPersonalDetails.studentId = student.id ";

        return jdbcTemplate.query(query, rs -> {
            List<Student> list = new ArrayList<>();
            while (rs.next()) {
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
                list.add(student);
            }
            return list;
        });
    }

    @Override
    public void setSelectRecords(Student student, Scanner sc, Logger logger) {
        logger.info("Logger init to setSelectRecords");
        System.out.print("Press 1 to see all data || Press 2 to see random data := ");
        int check = sc.nextInt();
        if (check == 1) {
            List<Student> list = getAllStudents();
            for (Student e : list)
                System.out.print(e+"\n");
        } else if (check == 2) {
            System.out.println("Enter id:-");
            student.setStudentId(sc.nextInt());
            logger.info("Random Fetched data of ID {}",student.getStudentId());
            Student student1 = fetchRecords(student,logger);
            logger.info("Fetched data := {}",student1);
            System.out.print(student1);
        } else {
            System.out.print("Wrong Choice");
        }
    }

    @Override
    public boolean jsonInsert(Student student, Logger logger, Scanner sc) {
        logger.info("Logger init to jsonInsert");
        query = "insert into student (id, studentName,lastName) values(?, ?, ?)";
        String query1 = "insert into studentPersonalDetails(studentId, fatherName, motherName, address, dob) " +
                "values(?, ?, ?, ?, ?)";
        String query2 = "insert into studentMarks(studentId, english, hindi, maths, science, social, percentage) " +
                "values(?, ?, ?, ?, ?, ?, ?)";
        System.out.println("Enter the address of file:-");
        String filePath = sc.next();
        int count = 0;
        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = null;
            try {
                jsonObject = (JSONObject) parser.parse(new FileReader(filePath));
            } catch (IOException | ParseException e) {
                logger.error("Error at JsonInsert while fileReader ", e);
            }
            JSONArray array = (JSONArray) jsonObject.get("students");
            int i = 0;
            int i1 = 0;
            int i2 = 0;
            for (Object object : array) {
                JSONObject record = (JSONObject) object;
                student.setStudentId(Integer.parseInt(record.get("id").toString()));
                student.setStudentName((String) record.get("studentName"));
                student.setStudentLastName((String) record.get("lastName"));
                student.setFatherName((String) record.get("fatherName"));
                student.setMotherName((String) record.get("motherName"));
                student.setAddress((String) record.get("address"));
                student.setDob((String) record.get("dob"));
                student.setEnglish(Float.parseFloat(record.get("english").toString()));
                student.setHindi(Float.parseFloat(record.get("hindi").toString()));
                student.setMaths(Float.parseFloat(record.get("maths").toString()));
                student.setScience(Float.parseFloat(record.get("science").toString()));
                student.setSocial(Float.parseFloat(record.get("social").toString()));
                float total = student.getEnglish() + student.getHindi() + student.getMaths() + student.getScience() + student.getSocial();
                student.setPercentage((total * 100) / 500);

                i = this.jdbcTemplate.update(query,student.getStudentId(),student.getStudentName(),student.getStudentLastName());
                i1 = this.jdbcTemplate.update(query1,student.getStudentId(),student.getFatherName(),student.getMotherName(),
                        student.getAddress(), student.getDob());
                i2 = this.jdbcTemplate.update(query2, student.getStudentId(), student.getEnglish(), student.getHindi(),
                        student.getMaths(), student.getScience(), student.getSocial(), student.getPercentage());
            }
            if (i > 0 && i1 > 0 && i2 > 0) {
                logger.info("JSON Inserted!!");
                System.out.print("JSON Inserted!!");
                return true;
            } else {
                System.out.print("Not JSON Inserted!!");
                logger.warn("Not JSON Inserted!!");
                return false;
            }
        } catch (InputMismatchException e) {
          logger.error("Error at jsonInsert :-",e);
        }
        return false;
    }
}
