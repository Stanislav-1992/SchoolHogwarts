package ru.hogwarts.schoolspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.schoolspring.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
