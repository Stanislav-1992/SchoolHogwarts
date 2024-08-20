package ru.hogwarts.schoolspring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import ru.hogwarts.schoolspring.model.Faculty;
import ru.hogwarts.schoolspring.model.Student;
import ru.hogwarts.schoolspring.repositories.FacultyRepository;
import java.util.List;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {

    List<Faculty> savedFaculties;
    @LocalServerPort
    private int port;

    @Autowired

    private FacultyRepository facultyRepository;
    @Autowired

    private TestRestTemplate restTemplate;

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
        assertThat(newFaculty.getBody().getName()).isEqualTo(name);
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
                .getForEntity("http://localhost:" + port + "/faculty/" + newFaculty.getBody().getId(), Faculty.class);

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

        ResponseEntity<Faculty> facultyResponseEntity = restTemplate
                .getForEntity("http://localhost:" + port + "/faculty/color?color=" + newFaculty.getBody().getColor(), Faculty.class);

        assertThat(facultyResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(facultyResponseEntity.getBody()).isNotNull();
        assertThat(facultyResponseEntity.getBody().getColor()).isEqualTo(color);
        assertThat(facultyResponseEntity.getBody().getName()).isEqualTo(name);
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

        ResponseEntity<Faculty> facultyResponseEntity = restTemplate
                .getForEntity("http://localhost:" + port + "/faculty/name?name=" + newFaculty.getBody().getName(), Faculty.class);

        assertThat(facultyResponseEntity.getBody()).isNotNull();
        assertThat(facultyResponseEntity.getBody().getColor()).isEqualTo(color);
        assertThat(facultyResponseEntity.getBody().getName()).isEqualTo(name);
    }


    @Test
    public void getAllFacultyTest() throws Exception {
        ResponseEntity<List<Faculty>> response = restTemplate.exchange(
                "http://localhost:" + port + "/faculty", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Faculty>>() {
                });
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        List<Faculty> actualFaculty = response.getBody().stream().collect(Collectors.toList());
        assertEquals(savedFaculties, actualFaculty);
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
                ("http://localhost:" + port + "/faculty/" + newFaculty.getBody().getId(),
                        HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        assertNull(facultyResponseEntity.getBody());
        assertThat(facultyResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(facultyResponseEntity).isNull();
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

        editfaculty.setId(newFaculty.getBody().getId());


        ResponseEntity<Faculty> response = restTemplate
                .exchange("http://localhost:" + port + "/faculty", HttpMethod.PUT, new HttpEntity<>(editfaculty), Faculty.class);

        assertThat(response.getBody().getName()).isEqualTo(editfaculty.getName());
        assertThat(response.getBody().getColor()).isEqualTo(editfaculty.getColor());
        assertThat(response.getBody().getId()).isEqualTo(editfaculty.getId());
    }

    @Test
    public void findStudentsFromFacultyTest() throws Exception {

        final String name = "Тестовый факультет";
        final String color = "Тестовый цвет";

        final String nameStudent = "Тестовый";
        final int age = 101;

        Faculty faculty = new Faculty();

        faculty.setName(name);
        faculty.setColor(color);

        ResponseEntity<Faculty> newFaculty = restTemplate
                .postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);

        Student student = new Student();

        student.setName(nameStudent);
        student.setAge(age);
        student.setFaculty(newFaculty.getBody());

        ResponseEntity<Student> newStudent = restTemplate
                .postForEntity("http://localhost:" + port + "/student", student, Student.class);

        List<Student> list = restTemplate.getForObject("http://localhost:" + port + "/faculty/" + newFaculty.getBody().getId() + "/students",List.class);

        assertThat(list).isNotNull();
        assertThat(list.contains(newStudent.getBody()));
    }

}
