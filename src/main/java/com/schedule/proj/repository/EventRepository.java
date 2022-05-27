package com.schedule.proj.repository;


import com.schedule.proj.model.Event;

import com.schedule.proj.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByUser(User user);
    Event findEventById(Long id);
}
