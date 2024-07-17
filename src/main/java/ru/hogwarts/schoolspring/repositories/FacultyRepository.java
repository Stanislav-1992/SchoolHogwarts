package ru.hogwarts.schoolspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.schoolspring.model.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}
