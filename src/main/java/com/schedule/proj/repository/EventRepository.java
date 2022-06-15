package com.schedule.proj.repository;

import com.schedule.proj.model.Event;
import com.schedule.proj.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;



@Repository
public interface EventRepository extends JpaRepository<Event, Long> {



    List<Event> findAll();

    Event findEventByName(String name);

    List<Event> findAllByUser(User user);

   Event findEventById(Integer id);


}
