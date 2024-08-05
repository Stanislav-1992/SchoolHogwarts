package ru.hogwarts.schoolspring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.schoolspring.controller.StudentController;
import ru.hogwarts.schoolspring.model.Faculty;
import ru.hogwarts.schoolspring.model.Student;
import ru.hogwarts.schoolspring.repositories.AvatarRepository;
import ru.hogwarts.schoolspring.repositories.FacultyRepository;
import ru.hogwarts.schoolspring.repositories.StudentRepository;
import ru.hogwarts.schoolspring.service.AvatarService;
import ru.hogwarts.schoolspring.service.FacultyService;
import ru.hogwarts.schoolspring.service.StudentService;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StudentController.class)
public class StudentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private AvatarRepository avatarRepository;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private StudentService studentService;

    @SpyBean
    private AvatarService avatarService;
    @SpyBean
    private FacultyService facultyService;


    @Test
    public void addStudentTest() throws Exception {
        final String name = "Test";
        final int age = 10;
        final long id = 1100;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setId(id);

        ArgumentCaptor<Student> argumentCaptor = ArgumentCaptor.forClass(Student.class);
        Mockito.verify(studentRepository).save(argumentCaptor.capture());
        Student actual = argumentCaptor.getValue();

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

    }

    @Test
    public void getAllStudentTest() throws Exception {
        final String name = "Test";
        final int age = 10;
        final long id = 1100;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setId(id);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findAll()).thenReturn(List.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        Mockito.verify(studentRepository, times(1)).save(any(Student.class));
        Mockito.verify(studentRepository, times(1)).findAll();
    }
    @Test
    public void getStudentByIdTest() throws Exception {
        final String name = "Test";
        final int age = 10;
        final long id = 1100;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setId(id);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

        Mockito.verify(studentRepository, times(1)).save(any(Student.class));
        Mockito.verify(studentRepository, times(1)).findById(any(Long.class));
    }

    @Test
    public void getStudentByAgeTest() throws Exception {
        final String name = "Test";
        final int age = 10;
        final long id = 1100;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setId(id);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.getAllStudentByAge(any(Integer.class))).thenReturn(List.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age?age=" + age)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].age").value(age));

        Mockito.verify(studentRepository, times(1)).save(any(Student.class));
        Mockito.verify(studentRepository, times(1)).getAllStudentByAge(any(Integer.class));
    }

    @Test
    public void getStudentByAgeBetweenTest() throws Exception {
        final String name = "Test";
        final int age = 10;
        final long id = 1100;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setId(id);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findByAgeBetween(any(Integer.class), any(Integer.class))).thenReturn(List.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/ageBetween?min=5&max=10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].age").value(age));

        Mockito.verify(studentRepository, times(1)).save(any(Student.class));
        Mockito.verify(studentRepository, times(1)).
                findByAgeBetween(any(Integer.class), (any(Integer.class)));
    }

    @Test
    public void deleteStudentTest() throws Exception {

        final String name = "Test";
        final int age = 10;
        final long id = 1;

        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setId(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + id)
                        .content(objectMapper.writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("Faculty deleted successfully"));

    }


    @Test
    public void editStudentById() throws Exception {
        final String name = "Test";
        final int age = 10;
        final long id = 1100;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);
        studentObject.put("id", id);


        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setId(id);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

        Mockito.verify(studentRepository, times(1)).findById(any(Long.class));
        Mockito.verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    public void findFacultyFromStudentTest() throws Exception {
        final String name = "Test";
        final int age = 10;
        final long id = 1100;

        final String nameFaculty = "TestFaculty";
        final String color = "TestColor";
        final long idFaculty = 1000;

        Faculty faculty = new Faculty();
        faculty.setId(idFaculty);
        faculty.setName(nameFaculty);
        faculty.setColor(color);

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);
        studentObject.put("id", id);

        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setId(id);
        student.setFaculty(faculty);

        when(studentService.findFacultyFromStudent(any(Long.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + id + "/faculty")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value(color))
                .andExpect(jsonPath("$.name").value(nameFaculty))
                .andExpect(jsonPath("$.id").value(idFaculty));

        Mockito.verify(studentService, times(1)).findFacultyFromStudent(any(Long.class));
    }
}