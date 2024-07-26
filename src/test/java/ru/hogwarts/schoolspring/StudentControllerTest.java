package ru.hogwarts.schoolspring;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.schoolspring.controller.StudentController;
import ru.hogwarts.schoolspring.model.Faculty;
import ru.hogwarts.schoolspring.model.Student;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentsController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentsController).isNotNull();
    }

    @Test
    public void testGetStudent() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student", String.class))
                .isNotNull();
    }

    @Test
    public void addStudentTest() throws Exception {

        String name = "Тестовый add";
        int age = 1;

        Student student = new Student();
        student.setName(name);
        student.setAge(age);

        ResponseEntity<Student> response = restTemplate
                .postForEntity("http://localhost:" + port + "/student", student, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo(name);
        assertThat(response.getBody().getAge()).isEqualTo(age);
    }

    @Test
    public void getStudentByIdTest() throws Exception {

        String name = "Тестовый add1";
        int age = 2;

        Student student = new Student();
        student.setName(name);
        student.setAge(age);

        ResponseEntity<Student> response = restTemplate
                .postForEntity("http://localhost:" + port + "/student", student, Student.class);

        ResponseEntity<Student> responseEntity = restTemplate
                .getForEntity("http://localhost:" + port + "/student/" + response.getBody().getId(), Student.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getAge()).isEqualTo(age);
        assertThat(responseEntity.getBody().getName()).isEqualTo(name);
    }

    @Test
    public void getStudentByAgeTest() throws Exception {

        String name = "Тестовый add3";
        int age = 3;

        Student student = new Student();
        student.setName(name);
        student.setAge(age);

        Student response = restTemplate
                .postForObject("http://localhost:" + port + "/student", student, Student.class);

        List listStudent = restTemplate
                .getForObject("http://localhost:" + port + "/student/age?age=" + response.getAge(), List.class);


        assertThat(listStudent).isNotNull();
        assertThat(listStudent.contains(response.getName()));
        assertThat(listStudent.contains(response.getAge()));
        assertThat(listStudent.contains(response.getId()));
    }

    @Test
    public void getAllStudentTest() throws Exception {

        String name = "Тестовый add4";
        int age = 4;

        Student student = new Student();
        student.setName(name);
        student.setAge(age);

        Student response = restTemplate
                .postForObject("http://localhost:" + port + "/student", student, Student.class);


        List listStudent = restTemplate.getForObject("http://localhost:" + port + "/student", List.class);

        assertThat(listStudent).isNotNull();
        assertThat(listStudent.contains(response.getName()));
        assertThat(listStudent.contains(response.getAge()));
        assertThat(listStudent.contains(response.getId()));
    }


    @Test
    public void getStudentByAgeBetweenTest() throws Exception {

        String name = "Тестовый add5";
        int age = 5;

        Student student = new Student();
        student.setName(name);
        student.setAge(age);

        Student response = restTemplate
                .postForObject("http://localhost:" + port + "/student", student, Student.class);

        List listStudent = restTemplate.getForObject("http://localhost:" + port + "/student/ageBetween?min=5&max=10", List.class);

        assertThat(listStudent).isNotNull();
        assertThat(listStudent.contains(response.getName()));
        assertThat(listStudent.contains(response.getAge()));
        assertThat(listStudent.contains(response.getId()));
    }

    @Test
    public void deleteStudentTest() throws Exception {
        final String name = "Удаление add6";
        final int age = 6;

        Student student = new Student();
        student.setName(name);
        student.setAge(age);

        ResponseEntity<Student> response = restTemplate
                .postForEntity("http://localhost:" + port + "/student", student, Student.class);

        ResponseEntity<Void> responseEntity = restTemplate.exchange("http://localhost:" + port + "/student/" + response.getBody().getId(), HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        assertNull(responseEntity.getBody());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }


    @Test
    public void editStudentById() throws Exception {
        final String name = "Тестовый add7";
        final int age = 7;


        final String editName = "Тестовый edit7";
        final int editAge = 77;

        Student student = new Student();
        student.setName(name);
        student.setAge(age);


        ResponseEntity<Student> response = restTemplate
                .postForEntity("http://localhost:" + port + "/student", student, Student.class);

        Student editStudent = new Student();
        editStudent.setName(editName);
        editStudent.setAge(editAge);

        editStudent.setId(response.getBody().getId());


        ResponseEntity<Student> newResponse = restTemplate
                .exchange("http://localhost:" + port + "/student", HttpMethod.PUT, new HttpEntity<>(editStudent), Student.class);

        assertThat(newResponse.getBody().getName()).isEqualTo(editStudent.getName());
        assertThat(newResponse.getBody().getAge()).isEqualTo(editStudent.getAge());
        assertThat(newResponse.getBody().getId()).isEqualTo(editStudent.getId());
    }

    @Test
    public void findFacultyFromStudentTest() {

        final String name = "Тест add8";
        final int age = 8;

        final String nameFaculty = "Тестовый факультет";
        final String color = "Тестовый цвет";

        Faculty faculty = new Faculty();
        faculty.setName(nameFaculty);
        faculty.setColor(color);

        Faculty newFaculty = restTemplate
                .postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);

        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setFaculty(faculty);

        Student addStudent = restTemplate
                .postForObject("http://localhost:" + port + "/student", student, Student.class);

        Faculty actual = restTemplate.getForObject("http://localhost:" + port + "/student" + addStudent.getId() + "/faculty", Faculty.class);
        assertThat(actual.getColor()).isEqualTo(faculty.getColor());
        assertThat(actual.getName()).isEqualTo(faculty.getName());

        //не работает
    }




}
