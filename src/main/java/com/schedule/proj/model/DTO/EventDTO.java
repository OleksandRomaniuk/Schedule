package com.schedule.proj.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Getter
@Data
public class EventDTO {

    private String name;

    private LocalDate date;

    private LocalTime time;

    private String color;

    private String description;

    public EventDTO() {

    }

}
