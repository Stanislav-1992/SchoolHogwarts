package ru.hogwarts.schoolspring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.schoolspring.model.Student;
import ru.hogwarts.schoolspring.repositories.StudentRepository;
import ru.hogwarts.schoolspring.service.StudentService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudentTest {
    @InjectMocks
    StudentService out;

    @Mock
    StudentRepository repository;

    Student STUDENT_1 =new Student ("Harry",12);

    Student STUDENT_2 =new Student ("Ron",11);

    List<Student> studentList;

    @BeforeEach
    void setUp (){
        STUDENT_1.setId(1L);
        STUDENT_2.setId(2L);
        studentList = new ArrayList<>(List.of(STUDENT_1,STUDENT_2));
    }

    @Test
    void addStudentTest() {
        Mockito.when (repository.save(STUDENT_1)).thenReturn (STUDENT_1);
        out.addStudent(STUDENT_1);
        Mockito.verify(repository,Mockito.times(1)).save(STUDENT_1);
        assertEquals(STUDENT_1,out.addStudent(STUDENT_1));
    }

    @Test
    void getStudentTest() {
        long ID=2L;
        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(STUDENT_2));
        assertEquals(STUDENT_2,out.getStudent(STUDENT_2.getId()));
        Mockito.verify(repository, Mockito.times(1)).findById(2L);

    }



    @Test
    void deleteStudentTest() {
        long ID=2L;
        Mockito.doNothing().when (repository).deleteById(ID);
        out.deleteStudent(ID);
        Mockito.verify(repository,Mockito.times(1)).deleteById(ID);

    }

    @Test
    void getAllStudentByAgeTest() {
        List <Student> EXP= List.of(STUDENT_1);
        int age = 12;
        Mockito.when(repository.getAllStudentByAge(age)).thenReturn(EXP);
        assertIterableEquals(EXP, out.getAllStudentByAge(age));
        Mockito.verify(repository,Mockito.times(1)).getAllStudentByAge(age);
    }
}

/*import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.hogwarts.schoolspring.impl.StudentServiceImp;
import ru.hogwarts.schoolspring.model.Student;
import service.StudentService;
import java.util.Collection;

public class StudentTest {

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
}*/
