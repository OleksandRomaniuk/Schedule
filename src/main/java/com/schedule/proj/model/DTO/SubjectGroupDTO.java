package com.schedule.proj.model.DTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SubjectGroupDTO {




    private  Integer group;

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    private  String  subjectname;


}
