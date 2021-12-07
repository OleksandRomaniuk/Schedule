package com.schedule.proj.repository;


import com.schedule.proj.model.Student;
import com.schedule.proj.model.Subject;
import com.schedule.proj.model.Teacher;
import org.apache.tomcat.jni.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Teacher getByUserId(Integer id);
}
