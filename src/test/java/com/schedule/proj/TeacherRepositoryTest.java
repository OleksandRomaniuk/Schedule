package com.schedule.proj;

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
public class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    public void getTeacherTest() {
        var teacher = teacherRepository.getById(1);
        Assert.assertEquals(teacher.getUser().getId(),2);
    }

}
