package ru.hogwarts.school2;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school2.controller.StudentController;
import ru.hogwarts.school2.model.Student;

import java.util.ArrayList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class School2ApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void contextLoads() {
        Assertions
                .assertThat(studentController)
                .isNotNull();
    }

    @Test
    public void testGetStudent() {
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student", String.class))
                .isNotNull();
    }

    @Test
    public void testPostStudent() {
        Student student = new Student();
        student.setId(1L);
        student.setAge(13);
        student.setName("Jack");

        Assertions
                .assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/student", student, String.class))
                .isNotNull();
    }
    @Test
    public void testGetStudentByAge() throws Exception {
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/age/21", ArrayList.class))
                .isInstanceOf(ArrayList.class);
    }

    @Test
    public void testGetStudentsAll() throws Exception {
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/all", ArrayList.class))
                .isExactlyInstanceOf(ArrayList.class);
    }

    @Test
    public void testFindStudentsByAgeBetween() throws Exception {
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/age/between?ageMin=14&ageMax=25", ArrayList.class))
                .isExactlyInstanceOf(ArrayList.class);
    }

    @Test
    public void testUpdateStudent() throws Exception {
        Student student = new Student();
        student.setId(6L);
        student.setName("Petr");
        student.setAge(20);
        HttpEntity<Student> entity = new HttpEntity<Student>(student);
        ResponseEntity<Student> response = testRestTemplate.exchange("http://localhost:" + port + "/student", HttpMethod.PUT, entity, Student.class);

        Assertions
                .assertThat(response.getBody()).isEqualTo(student);
    }

    @Test
    public void testDeleteStudent() throws Exception {
        Student student = new Student();
        student.setId(9L);
        student.setName("Bob");
        student.setAge(23);
        HttpEntity<Student> entity = new HttpEntity<Student>(student);
        ResponseEntity<Student> response = testRestTemplate.exchange("http://localhost:" + port + "/student/9", HttpMethod.DELETE, entity, Student.class);

        Assertions
                .assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }

}
