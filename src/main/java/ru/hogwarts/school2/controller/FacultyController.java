package ru.hogwarts.school2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school2.model.Faculty;
import ru.hogwarts.school2.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{idRead}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long idRead) {
        Faculty facultyGet = facultyService.readFaculty(idRead);
        if (facultyGet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyGet);
    }

//    @PostMapping
//    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
//        Faculty facultyCreate = facultyService.createFaculty(faculty);
//        if (facultyCreate == null) {
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.status(HttpStatus.CREATED).body(facultyCreate);
//    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facultyService.createFaculty(faculty));
    }


    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty facultyEdit = facultyService.updateFaculty(faculty);
        if (facultyEdit == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(facultyEdit);
    }

//    @DeleteMapping("{idDelete}")
//    public ResponseEntity deleteFaculty(@PathVariable Long idDelete) {
//        facultyService.deleteFaculty(idDelete);
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("color/{color}")
    public Collection<Faculty> getFacultyByColor(@PathVariable String color) {
        return facultyService.getFacultyByColor(color);
    }

    @GetMapping("colororname")
    public Collection<Faculty> getFacultyByColorOrName(@RequestParam(required = false) String color,
                                                       @RequestParam(required = false) String name) {
        return facultyService.findFacultyByColorAndName(color, name);
    }


    @GetMapping("all")
    public Collection<Faculty> allFaculty() {
        return facultyService.allFaculty();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        Faculty deletedFaculty = facultyService.deleteByOleg(id);
        if (deletedFaculty == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(deletedFaculty);
        }
    }

}
