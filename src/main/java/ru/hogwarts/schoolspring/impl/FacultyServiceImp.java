package ru.hogwarts.schoolspring.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.schoolspring.exeptions.FacultyNotFoundException;
import ru.hogwarts.schoolspring.model.Faculty;
import ru.hogwarts.schoolspring.model.Student;
import ru.hogwarts.schoolspring.repositories.FacultyRepository;
import ru.hogwarts.schoolspring.repositories.StudentRepository;
import ru.hogwarts.schoolspring.service.FacultyService;
import java.util.Collection;
import java.util.Optional;

@Service
public class FacultyServiceImp implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyServiceImp(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(null);
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFaculty(long id) {
        return facultyRepository.findById(id).orElseThrow(FacultyNotFoundException::new);
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        Optional<Faculty> facul = facultyRepository.findById(faculty.getId());
        if (facul.isPresent()) {
            return facultyRepository.save(faculty);
        }
        throw new FacultyNotFoundException();
    }

    @Override
    public Faculty deleteFaculty(long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isPresent()) {
            facultyRepository.deleteById(id);
            return faculty.get();
        }
        throw new FacultyNotFoundException();
    }

    @Override
    public Collection<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> getAllFacultyByColor(String color) {
        return facultyRepository.getAllFacultyByColor(color);
    }

    @Override
    public Collection<Faculty> getAllFacultyByColorOrName(String colorOrName) {
        return facultyRepository.getAllFacultyByColorIgnoreCaseOrNameIgnoreCase(colorOrName, colorOrName);
    }

    @Override
    public Collection<Student> findStudentsByFacultyId(long id) {
        return studentRepository.findByFaculty_Id(id);
    }
}
