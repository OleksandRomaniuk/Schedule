package com.schedule.proj.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.List;


@Data
@AllArgsConstructor
public class ScheduleDayDTO {
    private DayOfWeek name;
    private List<ScheduleTimeDTO> times;
}
