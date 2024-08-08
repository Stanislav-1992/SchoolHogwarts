package ru.hogwarts.schoolspring.service;

import ru.hogwarts.schoolspring.model.Faculty;
import ru.hogwarts.schoolspring.model.Student;

import java.util.Collection;

public interface FacultyService {
    Faculty addFaculty(Faculty faculty);

    Faculty getFaculty(long id);

    Faculty editFaculty(long id, Faculty faculty);

    void deleteFaculty(long id);

    Collection<Faculty> getAllFaculty();

    Collection<Faculty> getAllFacultyByColor(String color);

    Collection<Faculty> getAllFacultyByColorOrName(String colorOrName);

    Collection<Student> findStudentsByFacultyId(long id);
}
