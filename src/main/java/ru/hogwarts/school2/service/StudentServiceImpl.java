package ru.hogwarts.school2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school2.model.Faculty;
import ru.hogwarts.school2.model.Student;
import ru.hogwarts.school2.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

   private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student studentCreate) {
        logger.info("Was invoked method for creating student");
        return studentRepository.save(studentCreate);
    }

    @Override
    public Student readStudent(long idRead) {
        logger.info("Was invoked method for finding student by id");
        return studentRepository.findById(idRead).get();
    }

    @Override
    public Student updateStudent(Student studentUpdate) {
        logger.info("Was invoked method for updating student");
        return studentRepository.save(studentUpdate);
    }


    @Override
    public void deleteStudent(long idDelete) {
        logger.info("Was invoked method for deleting student");
        studentRepository.deleteById(idDelete);
    }

    @Override
    public Collection<Student> getStudentByAge(int ageFilter) {
        logger.info("Was invoked method for finding all students by age");
        return studentRepository.findAllByAge(ageFilter);
    }

    @Override
    public Collection<Student> findByAgeBetween(int ageMin, int ageMax) {
        logger.info("Was invoked method for finding all students within age interval");
        return studentRepository.findAllByAgeBetween(ageMin,ageMax);
    }


    @Override
    public Collection<Student> allStudent() {
        logger.info("Was invoked method for finding all students");
        return studentRepository.findAll();
    }

    @Override
    public Collection<Student> findByFacultyId(Long facultyID) {
        logger.info("Was invoked method for finding all students by faculty id");
        return studentRepository.findStudentByFacultyId(facultyID);
    }

    @Override
    public Faculty findFacultyOfStudent(Long studentId) {
        logger.info("Was invoked method for finding faculty of student");
        Student currentStudent = studentRepository.getById(studentId);
        return currentStudent.getFaculty();
    }

    @Override
    public Integer studentsTotalNumber() {
        logger.info("Was invoked method for calculating total number of students sample");
        return studentRepository.getStudentsTotalNumber();
    }

    @Override
    public Integer studentsAverageAge() {
        logger.info("Was invoked method for finding average age of students sample");
        return studentRepository.getStudentsAverageAge();
    }

    @Override
    public Collection<Student> lastFiveStudents() {
        logger.info("Was invoked method for finding 5 last added students");
        return studentRepository.lastFiveStudents();
    }

    @Override
    public Collection<String> sortedAZListOfAllStudedentsNames() {
        logger.info("Was invoked method for finding AZ sorted names list of students in UPPER CASE");
        List<Student> foundAll = studentRepository.findAll();
        return foundAll.stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(s -> s.startsWith("А"))
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public double studentsAverageAgeStream() {
        List<Student> foundAll = studentRepository.findAll();
        return foundAll.stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }

    public List<Student> getNameStudentsToConsole() {

        List<Student> students = studentRepository.findAll();

        Thread thread1 = new Thread(() -> {
            System.out.println("Student №3:  " + students.get(2).getName());
            System.out.println("Student №4:  " + students.get(3).getName());
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("Student №5:  " + students.get(4).getName());
            System.out.println("Student №6:  " + students.get(5).getName());
        });

        System.out.println("Student №1:  " + students.get(0).getName());
        System.out.println("Student №2:  " + students.get(1).getName());
        thread1.start();
        thread2.start();

//        System.out.println("Student №7:  " + students.get(6).getName());
//        System.out.println("Student №8:  " + students.get(7).getName());

        return students;
    }

    public void getNameStudentsToConsoleSynch() {
        List<Student> students = studentRepository.findAll();

        Thread thread1 = new Thread(() -> {
            printName(students,2);
            printName(students,3);
        });

        Thread thread2 = new Thread(() -> {
            printName(students,4);
            printName(students,5);
        });

        printName(students,0);
        printName(students,1);
        thread1.start();
        thread2.start();

    }

    public synchronized void printName(List<Student> students, int serialNumber) {
        String infoForPrint = "Student №" + serialNumber + ": " +
                students.get(serialNumber).getName();
        System.out.println(infoForPrint);
    }
}

