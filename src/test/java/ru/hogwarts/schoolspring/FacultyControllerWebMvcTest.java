package ru.hogwarts.schoolspring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.schoolspring.controller.FacultyController;
import ru.hogwarts.schoolspring.model.Faculty;
import ru.hogwarts.schoolspring.model.Student;
import ru.hogwarts.schoolspring.repositories.AvatarRepository;
import ru.hogwarts.schoolspring.repositories.FacultyRepository;
import ru.hogwarts.schoolspring.repositories.StudentRepository;
import ru.hogwarts.schoolspring.service.FacultyService;
import ru.hogwarts.schoolspring.service.StudentService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FacultyController.class)
public class FacultyControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private FacultyRepository facultyRepository;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private AvatarRepository avatarRepository;
    @SpyBean
    private FacultyService facultyService;
    @SpyBean
    private StudentService studentService;

    private TestRestTemplate template;

    @Test
    public void getFacultyInfoTest() throws Exception {
        final Long id = 1L;
        final String name = "IT";
        final String color = "red";

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName("Faculty1");
        faculty.setColor("green");

        Faculty newfaculty = new Faculty();
        newfaculty.setId(id);
        newfaculty.setName(name);
        newfaculty.setColor(color);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        Mockito.verify(facultyRepository, times(1)).findById(any(Long.class));
    }

    @Test
    public void createFacultyTest() throws Exception {
        final Long id = 1L;
        final String name = "IT";
        final String color = "red";

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName("Faculty1");
        faculty.setColor("green");

        Faculty newfaculty = new Faculty();
        newfaculty.setId(id);
        newfaculty.setName(name);
        newfaculty.setColor(color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(newfaculty);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(faculty.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        Mockito.verify(facultyRepository, times(1)).save(any(Faculty.class));
    }

    @Test
    void editFacultyTest() throws Exception {
        final Long id = 1L;
        final String name = "IT";
        final String color = "red";
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);
        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        Mockito.verify(facultyRepository, times(1)).findById(any(Long.class));
        Mockito.verify(facultyRepository, times(1)).save(any(Faculty.class));
    }

    @Test
    void deleteFacultyTest() throws Exception {
        final Long id = 1L;
        Faculty faculty = new Faculty();
        faculty.setId(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + id)
                        .content(objectMapper.writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    void findFacultyByColorTest() throws Exception {
        final Long id = 1L;
        final String name = "IT";
        final String color = "red";

        List<Faculty> faculties = new ArrayList<>();
        faculties.add(new Faculty(name, color));

        when(facultyRepository.getAllFacultyByColorIgnoreCaseOrNameIgnoreCase(eq(color), name)).thenReturn(faculties);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/facultyByColor?color=" + color)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].color").value(color));

        Mockito.verify(facultyRepository, times(1)).
                getAllFacultyByColorIgnoreCaseOrNameIgnoreCase(any(String.class), any(String.class));
    }

    @Test
    void findFacultyByNameTest() throws Exception {
        final Long id = 1L;
        final String name = "IT";
        final String color = "red";

        List<Faculty> faculties = new ArrayList<>();
        faculties.add(new Faculty(name, color));

        when(facultyRepository.getAllFacultyByColorIgnoreCaseOrNameIgnoreCase(eq(name), color)).thenReturn(faculties);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/facultyByNameOrColor?name=" + name)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        Mockito.verify(facultyRepository, times(1)).
                getAllFacultyByColorIgnoreCaseOrNameIgnoreCase(any(String.class), any(String.class));
    }

    @Test
    void getStudentsOfFacultyTest() throws Exception {
        final String name = "Test faculty";
        final String color = "test";
        final long id = 1;

        final String nameStudent = "Test";
        final int age = 10;
        final long idStudent = 1100;

        Student student = new Student();
        student.setName(nameStudent);
        student.setAge(age);
        student.setId(idStudent);


        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColor(color);
        faculty.setId(id);

        student.setFaculty(faculty);


        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);
        facultyObject.put("id", id);


        when(facultyService.findStudentsByFacultyId(any(Long.class))).thenReturn(List.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + id + "/students")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value(nameStudent))
                .andExpect(jsonPath("$[0].age").value(age));

        Mockito.verify(facultyService, times(1)).
                findStudentsByFacultyId(any(Long.class));
    }
}