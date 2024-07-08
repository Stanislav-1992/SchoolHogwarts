package ru.hogwarts.schoolspring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.hogwarts.schoolspring.impl.FacultyServiceImp;
import ru.hogwarts.schoolspring.model.Faculty;
import service.FacultyService;

import java.util.Collection;

public class FacultyServiceTest {

    private final FacultyService facultyService = new FacultyServiceImp();

    private final Faculty testFaculty = new Faculty("Химии", "оранжевый");
    private final Faculty testFaculty1 = new Faculty("Биологии", "чёрный");

    @Test
    public void addFacultyTest() {
        Assertions.assertEquals(testFaculty, facultyService.addFaculty(testFaculty));
    }

    @Test
    public void getFacultyTest() {
        facultyService.addFaculty(testFaculty);
        Assertions.assertEquals(testFaculty, facultyService.getFaculty(1));
    }

    @Test
    public void editFacultyTest() {
        facultyService.addFaculty(testFaculty);
        Assertions.assertEquals(testFaculty,facultyService.editFaculty(testFaculty));
    }

    @Test
    public void deleteFacultyTest() {
        facultyService.addFaculty(testFaculty);
        Assertions.assertEquals(testFaculty,facultyService.deleteFaculty(1));
    }

    @Test
    public void getAllFaculty() {
        facultyService.addFaculty(testFaculty);
        facultyService.addFaculty(testFaculty1);

        Collection<Faculty> faculty = facultyService.getAllFaculty();

        Assertions.assertEquals(2, faculty.size());
        Assertions.assertNotNull(faculty);

    }
    @Test
    public void getAllFacultyByColor() {
        facultyService.addFaculty(testFaculty);
        facultyService.addFaculty(testFaculty1);

        Collection<Faculty> faculties = facultyService.getAllFacultyByColor("зеленый");

        Assertions.assertEquals(1, faculties.size());

    }

}
