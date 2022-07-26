package ru.hogwarts.school2.service;

import ru.hogwarts.school2.model.Faculty;

import java.util.Collection;

public interface FacultyService {

    Faculty createFaculty(Faculty faculty);

    Faculty readFaculty(long idRead);

    Faculty updateFaculty(Faculty faculty);

    void deleteFaculty(long idDelete);

    Collection<Faculty> getFacultyByColor(String colorFilter);

    Collection<Faculty> findFacultyByColorAndName(String colorFilter, String nameFilter);

    Collection<Faculty> allFaculty();

    Faculty deleteByOleg(Long id);
}
