package ru.hogwarts.schoolspring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.hogwarts.schoolspring.impl.StudentServiceImp;
import ru.hogwarts.schoolspring.model.Student;
import service.StudentService;

import java.util.Collection;

public class StudentServiceTest {

    private final StudentService studentService = new StudentServiceImp();
    private final Student testStudent = new Student("Игорек", 10);
    private final Student checkStudent = new Student("Проверка", 100);

    @Test
    public void addStudentTest() {
        Assertions.assertEquals(testStudent, studentService.addStudent(testStudent));
    }

    @Test
    public void getStudentTest() {
        studentService.addStudent(testStudent);
        Assertions.assertEquals(testStudent, studentService.getStudent(1));
    }

    @Test
    public void editStudentTest() {
        studentService.addStudent(testStudent);
        Assertions.assertEquals(testStudent,studentService.editStudent(testStudent));
    }

    @Test
    public void deleteStudentTest() {
        studentService.addStudent(testStudent);
        Assertions.assertEquals(testStudent,studentService.deleteStudent(1));
    }

    @Test
    public void getAllStudents() {
        studentService.addStudent(testStudent);
        studentService.addStudent(checkStudent);

        Collection<Student> students = studentService.getAllStudent();

        Assertions.assertEquals(2, students.size());
        Assertions.assertNotNull(students);

    }
    @Test
    public void getAllStudentByAgeTest() {
        studentService.addStudent(new Student("Вася", 10));
        studentService.addStudent(new Student("Гриша", 11));
        studentService.addStudent(testStudent);

        Collection<Student> students = studentService.getAllStudentByAge(10);

        Assertions.assertEquals(2, students.size());

    }
}
