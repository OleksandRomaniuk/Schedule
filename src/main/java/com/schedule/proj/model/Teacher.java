package com.schedule.proj.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

// @EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Integer teacherId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    // @NotNull
    // @NotEmpty
    private String faculty;

    // @NotNull
    // @NotEmpty
    private String cathedra;

    // @NotEmpty
    private String rank;

    @OneToMany(mappedBy = "subjectTeacher", cascade = CascadeType.ALL)
//    @JsonIgnore
    private Set<Subject> subjects;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "accounts_id")
    private Accounts accounts;

    public Teacher(String email, String password, String firstName, String lastName,
                   String avatar, String faculty, String cathedra, String rank, Set<Subject> subjects) {
        this.user = new User(UserRole.TEACHER, email, password, firstName, lastName, avatar);
        this.faculty = faculty;
        this.cathedra = cathedra;
        this.rank = rank;
        this.subjects = subjects;
    }
}