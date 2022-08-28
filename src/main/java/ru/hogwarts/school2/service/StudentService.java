package ru.hogwarts.school2.service;

import ru.hogwarts.school2.model.Faculty;
import ru.hogwarts.school2.model.Student;

import java.util.Collection;

public interface StudentService {
    Student createStudent(Student student);

    Student readStudent(long idRead);

    Student updateStudent(Student student);

    void deleteStudent(long idDelete);

    Collection<Student> getStudentByAge(int ageFilter);

    Collection<Student> findByAgeBetween(int ageMin, int ageMax);

    Collection<Student> allStudent();

    Collection<Student> findByFacultyId(Long facultyID);

    Faculty findFacultyOfStudent(Long studentId);

    Integer studentsTotalNumber();

    Integer studentsAverageAge();

    Collection<Student> lastFiveStudents();

    Collection<String> sortedAZListOfAllStudedentsNames();

    double studentsAverageAgeStream();
}
