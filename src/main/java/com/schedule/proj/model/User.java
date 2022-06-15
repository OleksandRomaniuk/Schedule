package com.schedule.proj.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Event> event;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole userRole;

    @Column(unique=true)
    @Email(message = "Email should be valid")
    private String email;

    @NotNull
    @Size(min = 10, max = 70,
            message = "Password must be between 10 and 30 characters")
    private String password;

    @NotNull
    @Size(min = 2, max = 32,
            message = "First name must be between 4 and 32 characters")
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 32,
            message = "Last name must be between 4 and 32 characters")
    @Column(name = "last_name")
    private String lastName;




    public User(UserRole userRole,
                String email,
                String password,
                String firstName,
                String lastName,
                String avatar) {
        this.userRole = userRole;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id)  && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }


}
