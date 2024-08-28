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
import ru.hogwarts.schoolspring.controller.FacultyController;
import ru.hogwarts.schoolspring.impl.FacultyServiceImp;
import ru.hogwarts.schoolspring.impl.StudentServiceImp;
import ru.hogwarts.schoolspring.model.Faculty;
import ru.hogwarts.schoolspring.model.Student;
import ru.hogwarts.schoolspring.repositories.AvatarRepository;
import ru.hogwarts.schoolspring.repositories.FacultyRepository;
import ru.hogwarts.schoolspring.repositories.StudentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private FacultyServiceImp facultyServiceImp;
    @SpyBean
    private StudentServiceImp studentServiceImp;

    private TestRestTemplate testRestTemplate;

    @Test
    public void getFacultyInfoTest() throws Exception {
        final long id = 1L;
        final String name = "IT";
        final String color = "red";

        Faculty newfaculty = new Faculty();
        newfaculty.setId(id);
        newfaculty.setName(name);
        newfaculty.setColor(color);

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(newfaculty));
        mockMvc.perform(get("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        Mockito.verify(facultyRepository, times(1)).findById(any(Long.class));
    }

    @Test
    public void createFacultyTest() throws Exception {
        final long id = 1L;
        final String name = "IT";
        final String color = "red";

        Faculty newfaculty = new Faculty();
        newfaculty.setId(id);
        newfaculty.setName(name);
        newfaculty.setColor(color);

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(newfaculty);
        mockMvc.perform(post("/faculty")
                        .content(facultyObject.toString())
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
        final long id = 1L;
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

        mockMvc.perform(put("/faculty/"+ id)
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
        final long id = 1L;
        Faculty faculty = new Faculty();
        faculty.setId(id);

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));
        doNothing().when(facultyRepository).deleteById(any(Long.class));

        mockMvc.perform(delete("/faculty/" + id).content(objectMapper.writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

        Mockito.verify(facultyRepository, times(1)).findById(any(Long.class));
        Mockito.verify(facultyRepository, times(1)).deleteById(any(Long.class));
    }

    @Test
    void findFacultiesByColorTest() throws Exception {
        final String name = "IT";
        final String color = "red";

        List<Faculty> faculties = new ArrayList<>();
        faculties.add(new Faculty(name, color));

        when(facultyRepository.getAllFacultyByColor(eq(color))).thenReturn(faculties);
        mockMvc.perform(get("/faculty/facultyByColor?color=" + color)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].color").value(color));

        Mockito.verify(facultyRepository, times(1)).
                getAllFacultyByColor(any(String.class));
    }

    @Test
    void findFacultiesByNameTest() throws Exception {
        final String name = "IT";
        final String color = "red";

        List<Faculty> faculties = new ArrayList<>();
        faculties.add(new Faculty(name, color));

        when(facultyRepository.getAllFacultyByColorIgnoreCaseOrNameIgnoreCase(eq(name), eq(name))).thenReturn(faculties);
        mockMvc.perform(get("/faculty/facultyByNameOrColor?colorOrName=" + name)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value(name));

        Mockito.verify(facultyRepository, times(1)).
                getAllFacultyByColorIgnoreCaseOrNameIgnoreCase(any(String.class), any(String.class));
    }

    @Test
    void getStudentsOfFacultyTest() throws Exception {
        final String nameFaculty = "Test faculty";
        final String colorFaculty = "red";
        final long idFaculty = 1L;

        final String nameStudent = "Test student";
        final int ageStudent = 25;
        final long idStudent = 8L;

        Student student = new Student();
        student.setName(nameStudent);
        student.setAge(ageStudent);
        student.setId(idStudent);

        Faculty faculty = new Faculty();
        faculty.setName(nameFaculty);
        faculty.setColor(colorFaculty);
        faculty.setId(idFaculty);

        student.setFaculty(faculty);

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", nameFaculty);
        facultyObject.put("color", colorFaculty);
        facultyObject.put("id", idFaculty);

        when(facultyServiceImp.findStudentsByFacultyId(any(Long.class))).thenReturn(List.of(student));

        mockMvc.perform(get("/faculty/" + idFaculty + "/students")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value(nameStudent))
                .andExpect(jsonPath("$[0].age").value(ageStudent));

        Mockito.verify(facultyServiceImp, times(1)).
                findStudentsByFacultyId(any(Long.class));
    }
}