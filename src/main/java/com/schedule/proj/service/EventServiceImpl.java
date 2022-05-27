package com.schedule.proj.service;


import com.schedule.proj.model.Event;
import com.schedule.proj.model.User;
import com.schedule.proj.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class EventServiceImpl implements EventService {
    @Autowired
    EventRepository eventRepository;

    @Override
    public List<Event> findAllByUser(User user) {
        return eventRepository.findAllByUser(user);
    }

       @Override
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public void delete(Event event) {
        eventRepository.delete(event);
    }

    @Override
    public Event findEventById(Long id) {
        return eventRepository.findEventById(id);
    }


    @Override
    public Event editById(Long id, String title, String start, String end) {
        Event event = eventRepository.findEventById(id);
        event.setStart(start);
        event.setEnd(end);
        event.setTitle(title);
        event.setDescription(event.getDescription());
        return eventRepository.save(event);
    }

    @Override
    public Event editEventAndColour(Event event, String title, String description, String backgroundColour, String borderColour) {
        event.setTitle(title);
        event.setDescription(description);
        event.setBackgroundColor(backgroundColour);
        event.setBorderColor(borderColour);
        return eventRepository.save(event);
    }
}
