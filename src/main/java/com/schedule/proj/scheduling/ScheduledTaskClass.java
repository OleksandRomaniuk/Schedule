package com.schedule.proj.scheduling;

import com.schedule.proj.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTaskClass {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTaskClass.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private UserService userService;

//    @Scheduled(fixedRate = 3000)
//    public void reportCurrentTime() {
//        log.info("The time is now {}", dateFormat.format(new Date()));
//    }

    @Scheduled(cron = "0 */1 18-20 * * ?")
    public void scheduleTaskUsingCronExpression() {
        int availableUsers = userService.getAllUsers().size();
        log.info("Count of now available users is {}" , availableUsers);
    }

    @Scheduled(cron = "0 */1 18-20 * * ?")
    public void scheduleTaskUsingCronExpression2() {
        userService.evictAllUsers();
        log.info("Delete users cache");
    }
}