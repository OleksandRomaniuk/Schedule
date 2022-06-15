package com.schedule.proj.service;

import com.schedule.proj.exсeption.RegistrationException;
import com.schedule.proj.model.DTO.EventDTO;
import com.schedule.proj.model.Event;
import com.schedule.proj.model.User;
import com.schedule.proj.repository.EventRepository;
import com.schedule.proj.repository.UserRepository;
import com.schedule.proj.security.jwt.JwtProvider;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventService {


    @Autowired
    ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EventRepository eventReository;
    @Autowired
    PasswordService passwordService;
    @Autowired
    JwtProvider jwtProvider;

    public EventService() {

    }

    public Event findByName(String name){
        return eventReository.findEventByName(name);
    }

    public List<Event> findAll(Integer id){
        User user = userRepository.findOneById(id);
        return eventReository.findAllByUser(user);
    }

    public void deleteEvent(long id) {
        eventReository.deleteById(id);
    }

    public List<Event> getAllEvents() {
        return eventReository.findAll();
    }

    private static String convertToWeeks(Collection<Integer> weeks) {
            if (weeks.isEmpty())
                return "";
            List<Integer> sorted = weeks.stream().sorted().collect(Collectors.toList());

            if (sorted.size() == 1)
                return sorted.get(0).toString();

            int head = sorted.get(0);
            int tail = sorted.get(1);

            LinkedList<String> resStr = new LinkedList<>();

            for (int i = 1; i < weeks.size(); ++i) {
                if (sorted.get(i) - tail > 1) {
                    if (head == tail)
                        resStr.add(head + "");
                    else
                        resStr.add(head + "-" + tail);
                    head = sorted.get(i);
                }
                tail = sorted.get(i);
            }

        if (head == tail)
            resStr.add(head + "");
        else
            resStr.add(head + "-" + tail);

        return String.join(",", resStr);
    }
    public String  createEvent(EventDTO dto, HttpServletRequest request){
        String token = jwtProvider.getTokenFromRequest(request);
        String email = jwtProvider.getLoginFromToken(token);
        User user = userRepository.findUserByEmail(email);
        Event c = new Event();
        c.setColor(dto.getColor());
        c.setDate(dto.getDate());
        c.setDescription(dto.getDescription());
        c.setUser(user);
        c.setTime(dto.getTime());
        eventReository.save(c);
        return "Подію створено";
    }
}
