package ru.hogwarts.schoolspring.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Query;
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

    private final Logger log = LoggerFactory.getLogger(StudentService.class);

    public Student addStudent(Student student) {
        log.info("Был вызван метод, чтобы создать студента");
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
        log.info("Был вызван метод, чтобы получить студента");
        return studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
    }


    public void editStudent(long id, Student student) {
        log.info("Был вызван метод, чтобы редактировать студента");
        Student oldStu = studentRepository.findById(id).orElseThrow(FacultyNotFoundException::new);
        oldStu.setName(student.getName());
        oldStu.setAge(student.getAge());
        studentRepository.save(oldStu);
        }

    public void deleteStudent(long id) {
        log.info("Был вызван метод, чтобы удалить студента");
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            studentRepository.deleteById(id);
            student.get();
            return;
        }
        log.warn("Студент по id {} не был найден для удаления", id);
        throw new StudentNotFoundException();
    }


    public Collection<Student> getAllStudent() {
        log.info("Был вызван метод, чтобы получить всех студентов");
        return studentRepository.findAll();
    }


    public Collection<Student> getAllStudentByAge(int age) {
        log.info("Был вызван метод, чтобы получить всех студентов по возрасту");
        return studentRepository.getAllStudentByAge(age);
    }

    public Collection<Student> getStudentByRangeAge(int min, int max) {
        log.info("Был вызван метод, чтобы получить список студентов по диапазону возраста");
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty findFacultyFromStudent(long id) {
        log.info("Был вызван метод, чтобы найти факультет студента");
        return getStudent(id).getFaculty();
    }

    public Integer getAllStudentsAsNumber() {
        log.info("Был вызван метод, чтобы получить общее количество студентов");
        return studentRepository.getAllStudentsAsNumber();
    }

    public Integer getAverageAgeOfStudents() {
        log.info("Был вызван метод, чтобы получить средний возраст студентов");
        return studentRepository.getAverageAgeOfStudents();
    }

    public List<Student> getLastFiveStudents() {
        log.info("Был вызван метод, чтобы получить 5 последних добавленных студентов");
        return studentRepository.getLastFiveStudents();
    }
}