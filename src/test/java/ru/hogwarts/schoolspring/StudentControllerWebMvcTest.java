package ru.hogwarts.schoolspring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.schoolspring.controller.StudentController;
import ru.hogwarts.schoolspring.impl.AvatarServiceImpl;
import ru.hogwarts.schoolspring.impl.FacultyServiceImp;
import ru.hogwarts.schoolspring.impl.StudentServiceImp;
import ru.hogwarts.schoolspring.model.Faculty;
import ru.hogwarts.schoolspring.model.Student;
import ru.hogwarts.schoolspring.repositories.AvatarRepository;
import ru.hogwarts.schoolspring.repositories.FacultyRepository;
import ru.hogwarts.schoolspring.repositories.StudentRepository;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    private StudentServiceImp studentServiceImp;
    @SpyBean
    private AvatarServiceImpl avatarServiceImpl;
    @SpyBean
    private FacultyServiceImp facultyServiceImp;


    @Test
    public void addStudentTest() throws Exception {
        final String name = "Test";
        final int age = 10;
        final long id = 1L;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setId(id);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        mockMvc.perform(post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

        Mockito.verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    public void getAllStudentTest() throws Exception {
        final String name = "Test";
        final int age = 10;
        final long id = 1L;

        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setId(id);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findAll()).thenReturn(List.of(student));

        mockMvc.perform(get("/student/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].age").value(age))
                .andExpect(jsonPath("$[0].id").value(id));


        Mockito.verify(studentRepository, times(1)).findAll();
    }

    @Test
    public void getStudentByIdTest() throws Exception {
        final long id = 1L;
        final String name = "IT";
        final int age = 11;

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        mockMvc.perform(get("/student/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

        Mockito.verify(studentRepository, times(1)).findById(any(Long.class));
    }

    @Test
    public void getStudentByAgeTest() throws Exception {
        final long id = 1L;
        final String name = "IT";
        final int age = 11;

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.getAllStudentByAge(eq(age))).thenReturn(List.of(student));
        mockMvc.perform(get("/student?age=" + age)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].age").value(age));

        Mockito.verify(studentRepository, times(1)).getAllStudentByAge(any(Integer.class));
    }

    @Test
    public void getStudentByAgeBetweenTest() throws Exception {

        final long id = 1L;
        final String name = "IT";
        final int age = 11;
        final int minAge = 1;
        final int maxAge = 100;

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);
        studentObject.put("id", id);

        when(studentRepository.findByAgeBetween(any(Integer.class), any(Integer.class))).thenReturn(List.of(student));

        mockMvc.perform(get("/student/byAgeBetween?minAge=" + minAge + "&maxAge=" + maxAge)
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].age").value(age));
    }

    @Test
    public void deleteStudentTest() throws Exception {

        final long id = 1L;
        Student student = new Student();
        student.setId(id);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).deleteById(any(Long.class));


        mockMvc.perform(delete("/student/" + id)
                        .content(objectMapper.writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

        Mockito.verify(studentRepository, times(1)).findById(any(Long.class));
        Mockito.verify(studentRepository, times(1)).deleteById(any(Long.class));
    }


    @Test
    public void editStudentById() throws Exception {
        final long id = 1L;
        final String name = "IT";
        final int age = 55;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(put("/student/" + id)
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
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

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + id + "/faculty")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value(color))
                .andExpect(jsonPath("$.name").value(nameFaculty))
                .andExpect(jsonPath("$.id").value(idFaculty));

        Mockito.verify(studentRepository, times(1)).findById(any(Long.class));
        Mockito.verify(studentServiceImp, times(1)).findFacultyFromStudent(any(Long.class));
    }
}
