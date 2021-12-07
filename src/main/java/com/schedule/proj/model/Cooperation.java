package com.schedule.proj.model;


import javax.persistence.*;

@Entity
@Table(name = "Cooperation")
public class Cooperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }




    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    private Boolean status=false;


    public Cooperation() {
    }

    @Override
    public String toString() {
        return "Cooperation{" +
                "id=" + id +
                ", student=" + student +
                ", subject=" + subject +
                ", status=" + status +
                '}';
    }


}
