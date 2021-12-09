package com.schedule.proj.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubjectDTO {
    private String id;
    private String name;
    private String teacher;
    private String group;
    private String weeks;
}
