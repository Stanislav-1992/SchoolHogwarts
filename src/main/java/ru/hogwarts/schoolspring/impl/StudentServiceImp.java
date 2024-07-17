package ru.hogwarts.schoolspring.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.schoolspring.model.Student;
import ru.hogwarts.schoolspring.repositories.StudentRepository;
import service.StudentService;
import java.util.Collection;

@Service
public class StudentServiceImp implements StudentService {

    @Autowired
    private final StudentRepository studentRepository;

    public StudentServiceImp(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student getStudent(long id) {
        return studentRepository.findById(id).get();
    }

    @Override
    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    @Override
    public Collection<Student> getAllStudentByAge(int age) {
        return studentRepository.getAllStudentByAge(age);
    }
}