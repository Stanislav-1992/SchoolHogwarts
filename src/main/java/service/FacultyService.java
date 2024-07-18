package service;

import ru.hogwarts.schoolspring.model.Faculty;
import java.util.Collection;

public interface FacultyService {
    Faculty addFaculty(Faculty faculty);

    Faculty getFaculty(long id);

    Faculty editFaculty(Faculty faculty);

    void deleteFaculty(long id);

    public Collection<Faculty> getAllFaculty();

    public Collection<Faculty> getAllFacultyByColor(String color);
}
