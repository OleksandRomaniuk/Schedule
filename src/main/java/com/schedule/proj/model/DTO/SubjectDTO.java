package com.schedule.proj.model.DTO;

import com.schedule.proj.model.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO {
    private Integer subjectId;
    private String subjectName;
    private Teacher subjectTeacher;
    private String subjectGroup;
    private String weeks;
    private String subjectTime;
    private DayOfWeek dayOfWeek;
    private String subjectFaculty;
    private String subjectSpeciality;
    private String educationFormat;
}
