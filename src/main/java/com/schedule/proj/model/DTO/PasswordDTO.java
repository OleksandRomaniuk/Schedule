package com.schedule.proj.model.DTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PasswordDTO {

    private String oldPassword;
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
