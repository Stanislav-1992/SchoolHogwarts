package ru.hogwarts.schoolspring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.schoolspring.model.Faculty;
import ru.hogwarts.schoolspring.model.Student;
import ru.hogwarts.schoolspring.service.StudentService;
import java.util.Collection;

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
        return ResponseEntity.ok(studentService.getStudent(id));
    }

    @GetMapping("/all")
    public Collection<Student> getAllStudent() {
        return studentService.getAllStudent();
    }

    @GetMapping(params = "age")
    public Collection<Student> getAllStudentByAge(@RequestParam int age) {
        return studentService.getAllStudentByAge(age);
    }

    @PutMapping("{id}")
    public Student editStudent(@PathVariable Long id,@RequestBody Student student) {
        return studentService.editStudent(id, student);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/byAgeBetween")
    public ResponseEntity<Collection<Student>> findByAgeBetween(@RequestParam("minAge") int minAge,
                                                                @RequestParam("maxAge") int maxAge) {
        return ResponseEntity.ok(studentService.getStudentByRangeAge(minAge, maxAge));
    }

    @GetMapping("/{id}/faculty")
    public Faculty findFacultyFromStudent(@PathVariable long id) {
        return studentService.findFacultyFromStudent(id);
    }
}