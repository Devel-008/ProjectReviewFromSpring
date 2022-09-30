package com.review;

import org.slf4j.Logger;

import java.util.List;
import java.util.Scanner;

public interface StudentDao {
     public boolean updateDob(Student student, Logger logger );
     public boolean jsonInsert(Student student, Logger logger, Scanner sc);
     public boolean updateName(Student student, Logger logger);
     public boolean updateLName(Student student, Logger logger);
     public boolean updateFName(Student student, Logger logger);
     public boolean updateMName(Student student, Logger logger);
     public boolean updateHindi(Student student, Logger logger);
     public boolean updateMaths(Student student, Logger logger);
     public boolean updateSocial(Student student, Logger logger);
     public boolean updateAddress(Student student, Logger logger);
     public boolean updateEnglish(Student student, Logger logger);
     public boolean updateScience(Student student, Logger logger);
     public Student fetchRecords(Student student, Logger logger);
     public boolean insertRecords(Student student, Logger logger);
     public boolean deleteRecords(Student student, Logger logger);
     public boolean updateRecords(Student student, Logger logger);
     public boolean updatePercentage(Student student, Logger logger);
     public void setInsertRecords(Student student, Scanner sc, Logger logger);
     public void setUpdateRecords(Student student, Scanner sc, Logger logger);
     public void setSelectRecords(Student student, Scanner sc, Logger logger);
     public void setDeleteRecords(Student student, Scanner sc, Logger logger);
     List<Student> getAllStudents();
}
