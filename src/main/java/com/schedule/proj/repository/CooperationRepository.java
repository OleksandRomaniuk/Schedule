package com.schedule.proj.repository;

import com.schedule.proj.model.Student;
import com.schedule.proj.model.Subject;
import com.schedule.proj.model.Cooperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CooperationRepository extends JpaRepository<Cooperation,Integer> {

    @Query("SELECT c FROM Cooperation c WHERE c.student.studentId =:student and c.subject.subjectId =:subject")
    Cooperation  coopIsPresent(@Param("student") int student, @Param("subject") int subject);

    List<Cooperation> findAllByStudent_StudentId(Integer i);


    void deleteAllByStudentAndSubject(Student student, Subject subject);
}
