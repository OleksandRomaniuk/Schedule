package com.schedule.proj;

//import com.shedule.starter.mybeansspringbootstarter.service.MyBean;

import com.schedule.proj.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class ProjApplication {

    public static void main(String[] args) {

        SpringApplication.run(ProjApplication.class, args);

    }

}
