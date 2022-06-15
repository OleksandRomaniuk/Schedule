package com.schedule.proj.controller;


import com.schedule.proj.model.DTO.EventDTO;
import com.schedule.proj.model.Event;
import com.schedule.proj.security.jwt.JwtProvider;
import com.schedule.proj.service.EventService;
import com.schedule.proj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    UserService userService;
    @Autowired
    EventService eventService;
    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/CreateEvent/")
    String createEvent
            (HttpServletRequest req, @RequestBody EventDTO dto) {
        return eventService.createEvent(dto,req);
    }
    @PostMapping("/findAllEvents/")
    public List<Event> findEvent(Integer id){
        return eventService.findAll(id);
    }
    @PostMapping("/deleteEvent/")
    public void deleteEvent(Integer id){
         eventService.deleteEvent(id);
    }







}

