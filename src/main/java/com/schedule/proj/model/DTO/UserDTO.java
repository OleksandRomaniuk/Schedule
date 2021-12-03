package com.schedule.proj.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@AllArgsConstructor
@Data
public class UserDTO {

    @NotNull
    @NotEmpty(message = "First name could not be empty")
    @Size(min = 2, max = 32,
            message = "First name must be between 4 and 32 characters")
    private String firstName;

    @NotNull
    @NotEmpty(message = "Last name could not be empty")
    @Size(min = 2, max = 32,
            message = "Last name must be between 4 and 32 characters")
    private String lastName;

    @NotNull
    @NotEmpty(message = "Email could not be empty")
    @Email
    private String email;

    @NotNull
    @NotEmpty(message = "Password could not be empty")
    @Size(min = 8, message = "Password must contain more than 8 symbols")
    @Pattern(regexp = "[A-Za-z0-9_]*[A-Z]+[A-Za-z0-9_]*", message = "Password must contain Uppercase letter")
    private String password;

    @NotNull
    @NotEmpty
    private String role;

    public UserDTO() {

    }

}
