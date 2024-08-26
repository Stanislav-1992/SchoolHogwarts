package ru.hogwarts.schoolspring;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import ru.hogwarts.schoolspring.controller.FacultyController;
import ru.hogwarts.schoolspring.model.Faculty;
import ru.hogwarts.schoolspring.model.Student;
import ru.hogwarts.schoolspring.repositories.FacultyRepository;
import java.util.List;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FacultyController facultyController;

    @AfterEach
    void tearUp() {
        facultyRepository.deleteAll();
    }

    @Test
    public void addFacultyTest() throws Exception {

        final String name = "Name";
        final String color = "Color";

        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColor(color);

        ResponseEntity<Faculty> newFaculty = restTemplate
                .postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        assertThat(newFaculty.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(newFaculty.getBody()).getName()).isEqualTo(name);
        assertThat(newFaculty.getBody().getColor()).isEqualTo(color);
    }

    @Test
    public void getFacultyByIdTest() throws Exception {

        final String name = "Name";
        final String color = "Color";


        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColor(color);


        ResponseEntity<Faculty> newFaculty = restTemplate
                .postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        assertThat(newFaculty.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<Faculty> facultyResponseEntity = restTemplate
                .getForEntity("http://localhost:" + port + "/faculty/" +
                        Objects.requireNonNull(newFaculty.getBody()).getId(), Faculty.class);

        assertThat(facultyResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(facultyResponseEntity.getBody()).isNotNull();
        assertThat(facultyResponseEntity.getBody().getColor()).isEqualTo(color);
        assertThat(facultyResponseEntity.getBody().getName()).isEqualTo(name);
    }


    @Test
    public void getFacultyByColorTest() throws Exception {

        final String name = "Name";
        final String color = "Color";


        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColor(color);


        ResponseEntity<Faculty> newFaculty = restTemplate
                .postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        assertThat(newFaculty.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<List<Faculty>> response = restTemplate.exchange(
                "http://localhost:" + port + "/faculty?color=" + Objects.requireNonNull(newFaculty.getBody()).
                        getColor(), HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get(0).getColor()).isEqualTo(color);
        assertThat(response.getBody().get(0).getName()).isEqualTo(name);
    }


    @Test
    public void getFacultyByNameTest() throws Exception {

        final String name = "Имя1";
        final String color = "Цвет1";


        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColor(color);


        ResponseEntity<Faculty> newFaculty = restTemplate
                .postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        assertThat(newFaculty.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<List<Faculty>> response = restTemplate.exchange(
                "http://localhost:" + port + "/faculty?name=" + Objects.requireNonNull(newFaculty.getBody()).
                        getName(), HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Faculty>>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get(0).getColor()).isEqualTo(color);
        assertThat(response.getBody().get(0).getName()).isEqualTo(name);
    }


    @Test
    public void getAllFacultyTest() throws Exception {
        ResponseEntity<List<Faculty>> response = restTemplate.exchange(
                "http://localhost:" + port + "/faculty", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Faculty>>() {
                });
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        List<Faculty> actualFaculty = Objects.requireNonNull(response.getBody()).stream().toList();
        assertEquals(0, actualFaculty.size());
    }


    @Test
    public void deleteFacultyByIdTest() throws Exception {

        final String name = "Удаление факультета";
        final String color = "удаленный цвет факультета";

        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColor(color);

        ResponseEntity<Faculty> newFaculty = restTemplate
                .postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);

        ResponseEntity<Void> facultyResponseEntity = restTemplate.exchange
                ("http://localhost:" + port + "/faculty/" + Objects.requireNonNull(newFaculty.getBody()).getId(),
                        HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        assertNull(facultyResponseEntity.getBody());
        assertThat(facultyResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(facultyResponseEntity.getBody()).isNull();
    }

    @Test
    public void editFacultyTest() throws Exception {

        final String name = "Факультет";
        final String color = "Цвет";

        final String editName = "Edit Faculty";
        final String editColor = "Edit Color";

        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColor(color);


        ResponseEntity<Faculty> newFaculty = restTemplate
                .postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);

        Faculty editfaculty = new Faculty();
        editfaculty.setName(editName);
        editfaculty.setColor(editColor);

        editfaculty.setId(Objects.requireNonNull(newFaculty.getBody()).getId());


        ResponseEntity<Faculty> response = restTemplate
                .exchange("http://localhost:" + port + "/faculty/" + editfaculty.getId(), HttpMethod.PUT,
                        new HttpEntity<>(editfaculty), Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getName()).isEqualTo(editfaculty.getName());
        assertThat(response.getBody().getColor()).isEqualTo(editfaculty.getColor());
        assertThat(response.getBody().getId()).isEqualTo(editfaculty.getId());
    }

    @Test
    public void findStudentsFromFacultyTest1() throws Exception {

        final String name = "Тестовый факультет";
        final String color = "Тестовый цвет";
        final long facultyId = 20L;

        final String nameStudent = "Тестовый студент";
        final int age = 11;
        final long id = 1L;

        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColor(color);
        faculty.setId(facultyId);
        ResponseEntity<Faculty> newFaculty = restTemplate
                .postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);

        Student student = new Student();

        student.setName(nameStudent);
        student.setAge(age);
        student.setId(id);
        student.setFaculty(newFaculty.getBody());

        ResponseEntity<Student> newStudent = restTemplate
                .postForEntity("http://localhost:" + port + "/student", student, Student.class);

        ResponseEntity<List<Student>> response = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/{id}/students", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Student>>() {},facultyId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).contains(newStudent.getBody()));
        assertThat(response.getBody().equals(newStudent.getBody()));

    }
}
