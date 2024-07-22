package ru.hogwarts.schoolspring.service;

import ru.hogwarts.schoolspring.model.Faculty;
import ru.hogwarts.schoolspring.model.Student;

import java.util.Collection;

public interface StudentService {
    Student addStudent(Student student);

    Student getStudent(long id);

    Student editStudent(Student student);

    Student deleteStudent(long id);

    Collection<Student> getAllStudent();

    Collection<Student> getAllStudentByAge(int age);

    Collection<Student> getStudentByRangeAge(int minAge, int maxAge);

    Faculty findFacultyFromStudent(long id);
}
