package ru.hogwarts.schoolspring.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.schoolspring.exeptions.FacultyNotFoundException;
import ru.hogwarts.schoolspring.exeptions.StudentNotFoundException;
import ru.hogwarts.schoolspring.model.Faculty;
import ru.hogwarts.schoolspring.model.Student;
import ru.hogwarts.schoolspring.repositories.FacultyRepository;
import ru.hogwarts.schoolspring.repositories.StudentRepository;
import ru.hogwarts.schoolspring.service.StudentService;
import java.util.Collection;
import java.util.Optional;

@Service
public class StudentServiceImp implements StudentService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentServiceImp(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }
    @Override
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

    @Override
    public Student getStudent(long id) {
        return studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
    }

    @Override
    public Student editStudent(long id, Student student) {
        Student oldStu = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        oldStu.setName(student.getName());
        oldStu.setAge(student.getAge());
        return studentRepository.save(oldStu);
        }

    @Override
    public void deleteStudent(long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            studentRepository.deleteById(id);
            student.get();
            return;
        }
        throw new StudentNotFoundException();
    }

    @Override
    public Collection<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    @Override
    public Collection<Student> getAllStudentByAge(int age) {
        return studentRepository.getAllStudentByAge(age);
    }

    @Override
    public Collection<Student> getStudentByRangeAge(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Faculty findFacultyFromStudent(long id) {
        return getStudent(id).getFaculty();
    }
}