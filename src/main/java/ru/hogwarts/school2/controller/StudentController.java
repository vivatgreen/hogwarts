package ru.hogwarts.school2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school2.model.Faculty;
import ru.hogwarts.school2.model.Student;
import ru.hogwarts.school2.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{idRead}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long idRead) {
        Student studentGet = studentService.readStudent(idRead);
        if (studentGet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentGet);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student studentCreate = studentService.createStudent(student);
        if (studentCreate == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(studentCreate);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent (@RequestBody Student student) {
        Student studentEdit = studentService.updateStudent(student);
        if (studentEdit == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(studentEdit);
    }


    @DeleteMapping("{idDelete}")
    public ResponseEntity deleteStudent(@PathVariable Long idDelete) {
        studentService.deleteStudent(idDelete);
        return ResponseEntity.ok().build();
    }

    @GetMapping("age/{age}")
    public Collection<Student> getStudentByAge(@PathVariable int age) {
        return studentService.getStudentByAge(age);
    }

    @GetMapping("agebtw")
    public Collection<Student> getStudentByAgeRange(@RequestParam int ageMin,
                                                    @RequestParam int ageMax) {
        return studentService.findByAgeBetween(ageMin, ageMax);
    }

    @GetMapping("studentsbyfaculty")
    public Collection<Student> getStudentByFaculty(@RequestParam Long facultyId) {
        return studentService.findByFacultyId(facultyId);
    }

    @GetMapping("facultyofstudent")
    public Faculty getFacultyOfStudent(@RequestParam Long studentId) {
        return studentService.findFacultyOfStudent(studentId);
    }

    @GetMapping("all")
    public Collection<Student> allStudent() {
        return studentService.allStudent();
    }

    @GetMapping("total-number")
    public ResponseEntity<Integer> getStudentsTotalNumber() {
        Integer studentTotalNumber = studentService.studentsTotalNumber();
        if (studentTotalNumber == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(studentTotalNumber);
    }

    @GetMapping("average-age-sql")
    public ResponseEntity<Integer> getStudentsAverageAge() {
        Integer studentsAverageAge = studentService.studentsAverageAge();
        if (studentsAverageAge == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(studentsAverageAge);
    }


    @GetMapping("last-five")
    public Collection<Student> lastFiveStudents() {
        return studentService.lastFiveStudents();
    }

    @GetMapping("start-A")
    public Collection<String> getSortedAZListOfAllStudedentsNames() {
        return studentService.sortedAZListOfAllStudedentsNames();
    }

    @GetMapping("average-age-stream")
    public ResponseEntity<Double> getStudentsAverageAgeStream() {
        Double studentsAverageAge = studentService.studentsAverageAgeStream();
        if (studentsAverageAge == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(studentsAverageAge);
    }
}
