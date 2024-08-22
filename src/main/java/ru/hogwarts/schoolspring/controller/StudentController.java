package ru.hogwarts.schoolspring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.schoolspring.model.Faculty;
import ru.hogwarts.schoolspring.model.Student;
import ru.hogwarts.schoolspring.service.StudentService;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        studentService.getStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/allStudents")
    public Collection<Student> getAllStudent() {
        return studentService.getAllStudent();
    }

    @GetMapping(params = "age")
    public Collection<Student> getAllStudentByAge(@RequestParam int age) {
        return studentService.getAllStudentByAge(age);
    }

    @PutMapping("{id}")
    public void editStudent(@PathVariable long id,@RequestBody Student student) {
        studentService.editStudent(id, student);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(params = {"minAge", "maxAge"})
    public ResponseEntity<Collection<Student>> findByAgeBetween(@RequestParam(required = false) int minAge,
                                                                @RequestParam(required = false) int maxAge) {
        return ResponseEntity.ok(studentService.getStudentByRangeAge(minAge, maxAge));
    }

    @GetMapping("{id}/faculty")
    public Faculty findFacultyFromStudent(@PathVariable long id) {
        return studentService.findFacultyFromStudent(id);
    }

    @GetMapping("/allStudentsAsNumber")
    public Integer getAllStudentsAsNumber() {
        return studentService.getAllStudentsAsNumber();
    }

    @GetMapping("/averageAgeOfStudents")
    public Integer getAverageAgeOfStudents() {
        return studentService.getAverageAgeOfStudents();
    }

    @GetMapping("/lastFive")
    public List<Student> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    }

    @GetMapping("/by-name-started-A")
    public Collection<String> getAllStudentByNameStartedA() {
        return studentService.getAllStudentByNameStartedA();
    }

    @GetMapping("/average-age-stream")
    public Double getAverageAgeOfAllStudents() {
        return studentService.getAverageAgeOfAllStudents();
    }
}