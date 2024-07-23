package ru.hogwarts.schoolspring.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.schoolspring.exeptions.FacultyNotFoundException;
import ru.hogwarts.schoolspring.exeptions.StudentNotFoundException;
import ru.hogwarts.schoolspring.model.Faculty;
import ru.hogwarts.schoolspring.model.Student;
import ru.hogwarts.schoolspring.repositories.FacultyRepository;
import ru.hogwarts.schoolspring.repositories.StudentRepository;
import ru.hogwarts.schoolspring.service.StudentService;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImp implements StudentService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentServiceImp(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Student addStudent(Student student) {
        Faculty faculty = null;
        if (student.getFaculty() != null && student.getFaculty().getId() != null){
            faculty = facultyRepository.findById(student.getFaculty().getId())
                    .orElseThrow(FacultyNotFoundException::new);
        }
        student.setFaculty(faculty);
        student.setId(null);
        return studentRepository.save(student);
    }


    public Student getStudent(long id) {
        return studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
    }


    public Student editStudent(Student student) {
        Optional<Student> stu = studentRepository.findById(student.getId());
        if (stu.isPresent()) {
            return studentRepository.save(student);
        }
        throw new StudentNotFoundException();
    }


    public Student deleteStudent(long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            studentRepository.deleteById(id);
            return student.get();
        }
        throw new StudentNotFoundException();
    }


    public Collection<Student> getAllStudent() {
        return studentRepository.findAll();
    }


    public Collection<Student> getAllStudentByAge(int age) {
        return studentRepository.getAllStudentByAge(age);
    }

    public Collection<Student> getStudentByRangeAge(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty findFacultyFromStudent(long id) {
        return getStudent(id).getFaculty();
    }
}