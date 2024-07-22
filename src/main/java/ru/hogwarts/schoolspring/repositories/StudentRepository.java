package ru.hogwarts.schoolspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.schoolspring.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> getAllStudentByAge(int age);

    Collection<Student> findByAgeBetween(int min, int max);

    Collection<Student> findByFaculty_Id(long facultyId);
}
