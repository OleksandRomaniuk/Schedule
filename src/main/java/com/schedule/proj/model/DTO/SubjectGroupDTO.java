package com.schedule.proj.model.DTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SubjectGroupDTO {




    private  String group;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
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
