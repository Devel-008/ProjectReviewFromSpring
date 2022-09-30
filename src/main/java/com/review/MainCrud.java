package com.review;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

public class MainCrud {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Logger logger = LoggerFactory.getLogger(MainCrud.class);
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        StudentDao studentDao = context.getBean("studentDao",StudentDao.class);
        Student student = new Student();
        int choice;

        while (true) {
            logger.info("""
                        1] Press 1 to INSERT 2] Press 2 to DELETE 3]Press 3 to READ\s
                        4]Press 4 to UPDATE 5]Press 5 to insert data from any\040
                        JSON-File in database\s6]Press any other key to exit""");
            choice = sc.nextInt();
            switch (choice) {
                case 1 -> studentDao.setInsertRecords(student, sc, logger);
                case 2 -> studentDao.setDeleteRecords(student,sc,logger);
                case 3 -> studentDao.setSelectRecords(student,sc,logger);
                case 4 -> studentDao.setUpdateRecords(student,sc,logger);
                case 5 -> studentDao.jsonInsert(student,logger,sc);

                default -> {
                    logger.warn("\nDo you want to continue:= Press any key or else Press 0 to exit!!");
                    int n = sc.nextInt();
                    if (n == 0) {
                        logger.info("Process Successful");
                        return;
                    }
                }
            }
        }
    }
}
