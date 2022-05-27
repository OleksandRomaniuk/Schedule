package com.schedule.proj.service;



import com.schedule.proj.model.Event;
import com.schedule.proj.model.User;

import java.util.List;

public interface EventService {
    List<Event> findAllByUser(User user);
    Event save(Event event);
    void delete(Event event);
    Event findEventById(Long id);
    Event editById(Long id, String title, String start, String end);
    Event editEventAndColour(Event event, String title, String description, String backgroundColour, String borderColour);
}
