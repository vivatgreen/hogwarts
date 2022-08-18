package ru.hogwarts.school2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school2.model.Student;

import java.util.Collection;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    Collection<Student> findAllByAge(Integer ageFilter);

    Collection<Student> findAllByAgeBetween(Integer ageMin, Integer ageMax);

    Collection<Student> findStudentByFacultyId(Long facultyId);

    @Query (value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    Integer getStudentsTotalNumber();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    Integer getStudentsAverageAge();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    Collection<Student> lastFiveStudents();
}
