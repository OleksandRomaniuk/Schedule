package com.schedule.proj;

import com.schedule.proj.model.User;
import com.schedule.proj.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByEmailTest() {

        var user = userRepository.findUserByEmail("vkd@ukma.edu.ua");

        Assert.assertEquals(user.getFirstName(),"Ivan");
    }

    @Test
    public void findByIdTest() {

        var user = userRepository.findOneById(1);

        Assert.assertEquals(user.getLastName(),"Maister");
    }

}
