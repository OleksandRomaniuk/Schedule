package com.schedule.proj.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ScheduleTimeDTO {
    private String name;
    private List<SubjectDTO> subjects;
}
