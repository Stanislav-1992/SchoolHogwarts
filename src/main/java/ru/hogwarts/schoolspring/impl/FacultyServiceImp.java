package ru.hogwarts.schoolspring.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.schoolspring.model.Faculty;
import ru.hogwarts.schoolspring.repositories.FacultyRepository;
import service.FacultyService;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.boot.context.properties.ConfigurationPropertiesBean.getAll;

@Service
public class FacultyServiceImp implements FacultyService {

    @Autowired
    private final FacultyRepository facultyRepository;

    public FacultyServiceImp(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFaculty(long id) {
        return facultyRepository.findById(id).get();
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    @Override
    public Collection<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> getAllFacultyByColor(String color) {
        return facultyRepository.getAllFacultyByColor(color);
    }
}
