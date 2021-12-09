package com.schedule.proj.controller;


import com.schedule.proj.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/system")
public class SystemController {



    private void createStudents(int number_students) {
        for (int i = 1; i <= number_students; i++) {
            //User user = createOneUser(i, UserRole.ADMIN);
            //accountRepository.save(createOneAccount(user, i));
        }
    }



    private User createOneUser(int i, UserRole role) {
        // todo: replace with bean
        User n = new User();
        n.setEmail(i + "_" + role.name() + "@email.com");
        n.setPassword("password");
        n.setFirstName(i + "_" + role.name() + "FN");
        n.setLastName(i + "_" + role.name() + "LN");
        //n.setUserRole(role);
        return n;
    }



    public String getUser() {
        return "hi authentificaters";
    }


}
