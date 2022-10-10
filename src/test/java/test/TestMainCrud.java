package test;

import com.review.Student;
import com.review.StudentDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestMainCrud {

   Student student = new Student();
    Logger logger = LoggerFactory.getLogger(TestMainCrud.class);
    ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
    StudentDao dao = context.getBean("studentDao",StudentDao.class);
    SoftAssert softAssert = new SoftAssert();
    @BeforeClass
    public void setUp(){
        logger.info("Logger in test");
    }
    @Test(priority = 1)
    public void testOnInsert() {
        logger.info("Test on Insert started!!");
        student.setStudentId(4);
        student.setStudentName("Noumi");
        student.setStudentLastName("Bhura");
        student.setFatherName("Mahesh");
        student.setMotherName("Durga");
        student.setAddress("Leh");
        student.setDob("1999-10-18");
        student.setEnglish(66);
        student.setHindi(77);
        student.setMaths(98);
        student.setScience(89);
        student.setSocial(89);
        float total = student.getEnglish() + student.getHindi() + student.getMaths() + student.getScience() + student.getSocial();
        student.setPercentage((total * 100) / 500);

        dao.insertRecords(student,logger);
        Student student1 = dao.fetchRecords(student,logger);

        softAssert.assertEquals(student1.getStudentName(), student.getStudentName(), "Test on Insert-Name failed");
        softAssert.assertEquals(student1.getStudentLastName(),student.getStudentLastName(), "Test on Insert-LastName failed");
        softAssert.assertEquals(student1.getFatherName(),student.getFatherName(), "Test on Insert-FatherName failed");
        softAssert.assertEquals(student1.getMotherName(),student.getMotherName(), "Test on Insert-MotherName failed");
        softAssert.assertEquals(student1.getAddress(),student.getAddress(), "Test on Insert-Address failed");
        softAssert.assertEquals(student1.getDob(),student.getDob(), "Test on Insert-DOB failed");
        softAssert.assertEquals(student1.getEnglish(),student.getEnglish(), "Test on Insert-English failed");
        softAssert.assertEquals(student1.getHindi(),student.getHindi(), "Test on Insert-Hindi failed");
        softAssert.assertEquals(student1.getMaths(),student.getMaths(), "Test on Insert-Maths failed");
        softAssert.assertEquals(student1.getScience(),student.getScience(), "Test on Insert-Science failed");
        softAssert.assertEquals(student1.getSocial(),student.getSocial(), "Test on Insert-Social failed");
        softAssert.assertEquals(student1.getPercentage(), student.getPercentage(),"Test on Insert Percentage failed");
        softAssert.assertAll();
        logger.info("Data of ID {} ",student.getStudentId());
        logger.info("Data Inserted:= {}", student);
        logger.info("Data of ID {} ",student1.getStudentId());
        logger.info("Data Fetched:= {}", student1);
    }

    @Test(priority = 2)
    public void testOnUpdate() {
        logger.info("Test on Update started!!");
        student.setStudentId(4);
        student.setStudentName("GIGI");
        student.setStudentLastName("Soni");
        student.setFatherName("Deepak");
        student.setMotherName("Jasmine");
        student.setAddress("Banglore");
        student.setDob("1999-09-22");
        student.setEnglish(99);
        student.setHindi(98);
        student.setMaths(99);
        student.setScience(99);
        student.setSocial(99);
        float total = student.getEnglish() + student.getHindi() + student.getMaths() + student.getScience() + student.getSocial();
        student.setPercentage((total * 100) / 500);

        dao.updateRecords(student,logger);
        Student student1 = dao.fetchRecords(student,logger);

        softAssert.assertEquals(student1.getStudentName(), student.getStudentName(), "Test on Update-Name failed");
        softAssert.assertEquals(student1.getStudentLastName(),student.getStudentLastName(), "Test on Update-LastName failed");
        softAssert.assertEquals(student1.getFatherName(),student.getFatherName(), "Test on Update-FatherName failed");
        softAssert.assertEquals(student1.getMotherName(),student.getMotherName(), "Test on Update-MotherName failed");
        softAssert.assertEquals(student1.getAddress(),student.getAddress(), "Test on Update-Address failed");
        softAssert.assertEquals(student1.getDob(),student.getDob(), "Test on Update-DOB failed");
        softAssert.assertEquals(student1.getEnglish(),student.getEnglish(), "Test on Update-English failed");
        softAssert.assertEquals(student1.getHindi(),student.getHindi(), "Test on Update-Hindi failed");
        softAssert.assertEquals(student1.getMaths(),student.getMaths(), "Test on Update-Maths failed");
        softAssert.assertEquals(student1.getScience(),student.getScience(), "Test on Update-Science failed");
        softAssert.assertEquals(student1.getSocial(),student.getSocial(), "Test on Update-Social failed");
        softAssert.assertEquals(student1.getPercentage(), student.getPercentage(),"Test on Update Percentage failed");
        softAssert.assertAll();
        logger.info("Data of ID {} ",student.getStudentId());
        logger.info("Data updated:= {}", student);
        logger.info("Data of ID {} ",student1.getStudentId());
        logger.info("Data fetched:= {}", student1);
    }
    @Test(priority = 3)
    public void testOnDelete() {
        logger.info("Test on Delete started!!");
        student.setStudentId(4);
        boolean result = dao.deleteRecords(student,logger);
        softAssert.assertTrue(result, "Delete Test Failed");
        softAssert.assertAll();
    }
    @AfterClass
    public void tearDown(){
        logger.info("Logger Exit in TEST");
    }
}
