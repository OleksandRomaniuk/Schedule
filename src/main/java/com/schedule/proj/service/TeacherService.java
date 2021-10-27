package com.schedule.proj.service;

import com.schedule.proj.exсeption.DuplicateUserEmailException;
import com.schedule.proj.exсeption.TeacherNotFoundException;
import com.schedule.proj.exсeption.SubjectNotFoundException;
import com.schedule.proj.model.Teacher;
import com.schedule.proj.model.Subject;
import com.schedule.proj.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.schedule.proj.repository.TeacherRepository;


import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository, SubjectRepository subjectRepository) {
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
    }

    public Teacher createTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher getTeacher(Long id) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);

        if (optionalTeacher.isEmpty())
            throw new TeacherNotFoundException();

        return optionalTeacher.get();
    }

    public Teacher editTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public void deleteTeacher(Teacher teacher) {
        teacherRepository.delete(teacher);
    }

    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public long countTeachers() {
        return teacherRepository.count();
    }

    public Teacher addTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Transactional
    public Teacher updateTeacher(Teacher newTeacher) {
        Teacher teacher = teacherRepository.findById(newTeacher.getTeacherId())
                .orElseThrow(TeacherNotFoundException::new);

        if (newTeacher.getEmail() != null) {
            if (teacherRepository.findByEmail(newTeacher.getEmail()).isPresent())
                throw new DuplicateUserEmailException();
            teacher.setEmail(newTeacher.getEmail());
        }

        if (newTeacher.getCathedra() != null) {
            teacher.setCathedra(newTeacher.getCathedra());
        }

        if (newTeacher.getFaculty() != null) {
            teacher.setFaculty(newTeacher.getFaculty());
        }

        return teacher;
    }
    
}
