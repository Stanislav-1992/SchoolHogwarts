package ru.hogwarts.schoolspring.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.schoolspring.model.Student;
import service.StudentService;
import java.util.Collection;
import java.util.HashMap;

@Service
public class StudentServiceImp implements StudentService {

    private final HashMap<Long, Student> students = new HashMap<>();
    private long lastId = 0;

    @Override
    public Student addStudent(Student student) {
        student.setId(++lastId);
        students.put(lastId, student);
        return student;
    }

    @Override
    public Student getStudent(long id) {
        return students.get(id);
    }

    @Override
    public Student editStudent(Student student) {
        if (students.containsKey(student.getId())) {
            students.put(student.getId(), student);
            return student;
        }
        return null;
    }

    @Override
    public Student deleteStudent(long id) {
        return students.remove(id);
    }

    @Override
    public Collection<Student> getAllStudent() {
        return students.values();
    }

    @Override
    public Collection<Student> getAllStudentByAge(int age) {
        return students.values().stream()
                .filter(student -> student.getAge() == age)
                .toList();
    }
}