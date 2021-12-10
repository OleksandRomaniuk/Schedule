package com.schedule.proj;

import com.schedule.proj.repository.StudentRepository;
import com.schedule.proj.repository.SubjectRepository;
import com.schedule.proj.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SubjectRepositoryTest {

    @Autowired
    private SubjectRepository subjectRepository;
    @Test
    public void getStudentTest() {
        var subject = subjectRepository.getBySubjectId(1);
        Assert.assertEquals(subject.getSubjectName(),"Spring");
    }

}
