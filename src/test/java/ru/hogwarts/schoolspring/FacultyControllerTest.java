package ru.hogwarts.schoolspring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.schoolspring.controller.FacultyController;
import ru.hogwarts.schoolspring.model.Faculty;
import ru.hogwarts.schoolspring.model.Student;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

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

        final String name = "Название факультета";
        final String color = "цвет факультета";

        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColor(color);

        ResponseEntity<Faculty> newFaculty = restTemplate
                .postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        assertThat(newFaculty.getStatusCode()).isEqualTo(HttpStatus.OK);

        List facultyResponseEntity = restTemplate.getForObject("http://localhost:" + port + "/faculty", List.class);

        assertThat(facultyResponseEntity).isNotNull();
        assertThat(facultyResponseEntity.contains(newFaculty.getBody().getName()));
        assertThat(facultyResponseEntity.contains(newFaculty.getBody().getColor()));
        assertThat(facultyResponseEntity.contains(newFaculty.getBody().getId()));
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

        ResponseEntity<Void> facultyResponseEntity = restTemplate.exchange("http://localhost:" + port + "/faculty/" + newFaculty.getBody().getId(), HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        assertNull(facultyResponseEntity.getBody());
        assertThat(facultyResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
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