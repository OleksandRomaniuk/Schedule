package com.schedule.proj.model;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

//@Component
@Entity
@Table(name="subject")
public class Subject {


    @JsonIgnore
    @ManyToMany(mappedBy = "subjectsList")
    private List<Student> studentsList;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    @JsonIgnore
    private Teacher teacher;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="subject_id")
    private int subjectId;
//    @Column(name = "id", nullable = false)
    @Column(name="lesson_name")
    private String lessonName;

    @Column(name="day_of_week")
    private DayOfWeek dayOfWeek;

    @Column(name="lesson_time")
    private LocalTime lessonTime;

    @ElementCollection
    private Collection<Integer> weeks;

    @Column(name="lesson_group")
    private int lessonGroup;

    public int getSubjectId() {
        return subjectId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getLessonTime() {
        return lessonTime;
    }

    public void setLessonTime(LocalTime lessonTime) {
        this.lessonTime = lessonTime;
    }

    public int getLessonGroup() {
        return lessonGroup;
    }

    public void setLessonGroup(int lessonGroup) {
        this.lessonGroup = lessonGroup;
    }
}
