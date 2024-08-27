package ru.hogwarts.schoolspring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.schoolspring.model.Faculty;
import ru.hogwarts.schoolspring.model.Student;
import ru.hogwarts.schoolspring.service.FacultyService;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty addFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.getFaculty(id));
    }

    @GetMapping
    public Collection<Faculty> getAllFaculty() {
        return facultyService.getAllFaculty();
    }

    @GetMapping("/facultyByColor")
    public ResponseEntity<Collection<Faculty>> getAllFacultyByColor(@RequestParam("color") String color) {
        return ResponseEntity.ok(facultyService.getAllFacultyByColor(color));
    }

    @PutMapping("{id}")
    public Faculty editFaculty(@PathVariable Long id, @RequestBody Faculty faculty) {
        return facultyService.editFaculty(id, faculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/facultyByNameOrColor")
    public ResponseEntity<Collection<Faculty>> filterByColorOrName(@RequestParam("colorOrName") String colorOrName) {
        return ResponseEntity.ok(facultyService.getAllFacultyByColorOrName(colorOrName));
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<Collection<Student>> findStudentsByFacultyId(@PathVariable long id) {
        return ResponseEntity.ok(facultyService.findStudentsByFacultyId(id));
    }
}
