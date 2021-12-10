package com.schedule.proj;

import com.schedule.proj.repository.StudentRepository;
import com.schedule.proj.repository.TeacherRepository;
import com.schedule.proj.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StudentRepositoryTest {


    @Autowired
    private StudentRepository studentRepository;
    @Test
    public void getStudentSpecialityTest() {
        var student = studentRepository.getByUserId(1);
        Assert.assertEquals(student.getSpeciality(),"SoftwareEngineer");
    }
    @Test
    public void getStudentTest() {
        var student = studentRepository.getById(1);
        Assert.assertEquals(student.getStudentYear().toString(),"2");

    }
}
