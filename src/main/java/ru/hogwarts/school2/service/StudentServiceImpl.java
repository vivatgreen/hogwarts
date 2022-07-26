package ru.hogwarts.school2.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school2.model.Faculty;
import ru.hogwarts.school2.model.Student;
import ru.hogwarts.school2.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student studentCreate) {
        return studentRepository.save(studentCreate);
    }

    @Override
    public Student readStudent(long idRead) {
        return studentRepository.findById(idRead).get();
    }

    @Override
    public Student updateStudent(Student studentUpdate) {
        return studentRepository.save(studentUpdate);
    }


    @Override
    public void deleteStudent(long idDelete) {
        studentRepository.deleteById(idDelete);
    }

    @Override
    public Collection<Student> getStudentByAge(int ageFilter) {
        return studentRepository.findAllByAge(ageFilter);
    }

    @Override
    public Collection<Student> findByAgeBetween(int ageMin, int ageMax) {
        return studentRepository.findAllByAgeBetween(ageMin,ageMax);
    }


    @Override
    public Collection<Student> allStudent() {
        return studentRepository.findAll();
    }

    @Override
    public Collection<Student> findByFacultyId(Long facultyID) {
        return studentRepository.findStudentByFacultyId(facultyID);
    }

    @Override
    public Faculty findFacultyOfStudent(Long studentId) {
        Student currentStudent = studentRepository.getById(studentId);
        return currentStudent.getFaculty();
    }

    @Override
    public Integer studentsTotalNumber() {
        return studentRepository.getStudentsTotalNumber();
    }

    @Override
    public Integer studentsAverageAge() {
        return studentRepository.getStudentsAverageAge();
    }

    @Override
    public Collection<Student> lastFiveStudents() {
        return studentRepository.lastFiveStudents();
    }
}

