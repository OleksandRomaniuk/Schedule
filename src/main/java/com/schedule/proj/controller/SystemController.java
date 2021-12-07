package com.schedule.proj.controller;


import com.schedule.proj.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.schedule.proj.repository.AccountRepository;


@RestController
@RequestMapping("api/system")
public class SystemController {

    @Autowired
    public AccountRepository accountRepository;

    public String registerRoles() {

       try {
            int NUMBER_ADMINS = 1;
            int NUMBER_TEACHER = 10;
            int NUMBER_STUDENTS = 150;

            createAdmin(NUMBER_ADMINS);
            createTeacher(NUMBER_TEACHER);
            createStudents(NUMBER_STUDENTS);

            return "tables added";
        } catch (Exception ex) {
            return "The DataBase might have a set of test values, and method  " +
                    "\"GET http://localhost:8080/system/add\" " +
                    "was already called   \n \n Error:   " + ex.getMessage();
        }
    }

    private void createStudents(int number_students) {
        for (int i = 1; i <= number_students; i++) {
            //User user = createOneUser(i, UserRole.ADMIN);
            //accountRepository.save(createOneAccount(user, i));
        }
    }

    private void createTeacher(int number_teacher) {
        for (int i = 1; i <= number_teacher; i++) {

            User user = createOneUser(i, UserRole.TEACHER);
            accountRepository.save(createOneAccount(user, i));
        }
    }
    private void createAdmin(int numberAdmins) {
        for (int i = 1; i <= numberAdmins; i++) {

            User user = createOneUser(i, UserRole.STUDENT);
            accountRepository.save(createOneAccount(user, i));
        }
    }
    private Accounts createOneAccount(User user, int i) {
        Accounts a = new Accounts();
//        a.setTeacher(user);
//        a.setStudent(user);
        return a;
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
