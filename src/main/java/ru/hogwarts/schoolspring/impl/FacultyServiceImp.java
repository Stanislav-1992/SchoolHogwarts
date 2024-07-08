package ru.hogwarts.schoolspring.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.schoolspring.model.Faculty;
import service.FacultyService;

import java.util.Collection;
import java.util.HashMap;

@Service
public class FacultyServiceImp implements FacultyService {

    private final HashMap<Long, Faculty> schoolFaculty = new HashMap<>();
    private long facultyId;

    @Override
    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(++facultyId);
        schoolFaculty.put(facultyId, faculty);
        return faculty;
    }

    @Override
    public Faculty getFaculty(long id) {
        return schoolFaculty.get(id);
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        if (schoolFaculty.containsKey(faculty.getId())) {
            schoolFaculty.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    @Override
    public Faculty deleteFaculty(long id) {
        return schoolFaculty.remove(id);
    }

    @Override
    public Collection<Faculty> getAllFaculty() {
        return schoolFaculty.values();
    }

    @Override
    public Collection<Faculty> getAllFacultyByColor(String color) {
        return schoolFaculty.values().stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .toList();
    }
}
