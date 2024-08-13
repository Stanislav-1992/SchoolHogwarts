package ru.hogwarts.schoolspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.schoolspring.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> getAllStudentByAge(int age);

    Collection<Student> findByAgeBetween(int min, int max);

    Collection<Student> findByFaculty_Id(long facultyId);

    @Query(value = "SELECT COUNT(name) FROM students", nativeQuery = true)
    Integer getAllStudentsAsNumber();

    @Query(value = "SELECT AVG(age) FROM students", nativeQuery = true)
    Integer getAverageAgeOfStudents();

    @Query(value = "SELECT * FROM students ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLastFiveStudents();
}
