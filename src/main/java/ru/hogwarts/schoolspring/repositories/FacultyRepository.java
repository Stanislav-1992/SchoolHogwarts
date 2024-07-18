package ru.hogwarts.schoolspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.schoolspring.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> getAllFacultyByColor(String color);
}
