package ru.hogwarts.schoolspring.service;

import ru.hogwarts.schoolspring.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {
    Student addStudent(Student student);

    Student getStudent(long id);

    Student editStudent(Student student);

    void deleteStudent(long id);

    Collection<Student> getAllStudent();

    Collection<Student> getAllStudentByAge(int age);

}
