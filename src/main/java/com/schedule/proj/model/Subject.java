package com.schedule.proj.model;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

//@Component
@Entity
@Table(name="subject")
//@NoArgsConstructor
//@AllArgsConstructor
@Data
public class Subject implements Comparable<Subject> {

    @ManyToOne
    // todo: change to teacher_id
    @JoinColumn(name = "teacher_id")
    @JsonIgnore
    private Teacher subjectTeacher;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="subject_id")
    private Integer subjectId;

    @Size(min = 4, max = 200,
            message = "Lesson name must be between 4 and 32 characters")
    @Column(name="subject_name")
    private String subjectName;

   // @NotNull
  @Column(name="day_of_week")
    private DayOfWeek dayOfWeek;

   // @NotNull
   @Column(name="subject_time")
    private LocalTime subjectTime;

  //  @NotNull
    @ElementCollection
    private Collection<Integer> weeks;

   // @NotNull
    @Column(name="subject_group")
    private String subjectGroup;

   // @NotNull
    @Column(name="subject_faculty")
    private String subjectFaculty;


   // @NotNull
    @Column(name="subject_speciality")
    private String subjectSpeciality;

  //  @NotNull
    @Column(name="education_format")
    private String educationFormat;

    public Subject(Teacher subjectTeacher, String subjectName, DayOfWeek dayOfWeek,
                   LocalTime subjectTime, String subjectGroup, String subjectFaculty,
                   String subjectSpeciality, String educationFormat) {
        this.subjectTeacher = subjectTeacher;
        this.subjectName = subjectName;
        this.dayOfWeek = dayOfWeek;
        this.subjectTime = subjectTime;
        this.subjectGroup = subjectGroup;
        this.subjectFaculty = subjectFaculty;
        this.subjectSpeciality = subjectSpeciality;
        this.educationFormat = educationFormat;
    }

    public Subject(Integer subjectId, Teacher subjectTeacher, String subjectName, DayOfWeek dayOfWeek,
                   LocalTime subjectTime, String subjectGroup, String subjectFaculty,
                   String subjectSpeciality, String educationFormat) {
        this.subjectId = subjectId;
        this.subjectTeacher = subjectTeacher;
        this.subjectName = subjectName;
        this.dayOfWeek = dayOfWeek;
        this.subjectTime = subjectTime;
        this.subjectGroup = subjectGroup;
        this.subjectFaculty = subjectFaculty;
        this.subjectSpeciality = subjectSpeciality;
        this.educationFormat = educationFormat;
    }

    public Subject() {
    }
    public Integer getSubjectId() {
        return subjectId;
    }

    public Subject(Teacher subjectTeacher, @Size(min = 4, max = 200,
            message = "Lesson name must be between 4 and 32 characters") String subjectName, @NotNull LocalTime subjectTime, String subjectGroup) {
        this.subjectTeacher = subjectTeacher;
        this.subjectName = subjectName;
        this.subjectTime = subjectTime;
        this.subjectGroup = subjectGroup;
    }

    @Override
    public int compareTo(Subject subject) {
        return this.subjectName.compareTo(subject.getSubjectName());
    }
}
