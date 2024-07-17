package service;

import ru.hogwarts.schoolspring.model.Student;

import java.util.Collection;

public interface StudentService {
    Student addStudent(Student student);

    Student getStudent(long id);

    Student editStudent(Student student);

    void deleteStudent(long id);

    Collection<Student> getAllStudent();

    Collection<Student> getAllStudentByAge(int age);
}