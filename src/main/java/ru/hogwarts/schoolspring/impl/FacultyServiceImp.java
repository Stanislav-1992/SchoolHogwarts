package ru.hogwarts.schoolspring.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.schoolspring.exeptions.FacultyNotFoundException;
import ru.hogwarts.schoolspring.model.Faculty;
import ru.hogwarts.schoolspring.model.Student;
import ru.hogwarts.schoolspring.repositories.FacultyRepository;
import ru.hogwarts.schoolspring.repositories.StudentRepository;
import ru.hogwarts.schoolspring.service.FacultyService;
import java.util.Collection;
import java.util.Optional;

@Service
public class FacultyServiceImp implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyServiceImp(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    private final Logger log = LoggerFactory.getLogger(FacultyService.class);

    @Override
    public Faculty addFaculty(Faculty faculty) {
        log.info("Был вызван метод, чтобы создать факультет");
        faculty.setId(null);
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFaculty(long id) {
        log.info("Был вызван метод, чтобы получить факультет");
        return facultyRepository.findById(id).orElseThrow(FacultyNotFoundException::new);
    }
    @Override
    public void editFaculty(long id,Faculty faculty) {
        log.info("Был вызван метод, чтобы редактировать факультет");
        Faculty elderFaculty = facultyRepository.findById(id).orElseThrow(FacultyNotFoundException::new);
        elderFaculty.setName(faculty.getName());
        elderFaculty.setColor(faculty.getColor());
        facultyRepository.save(elderFaculty);
    }

    @Override
    public void deleteFaculty(long id) {
        log.info("Был вызван метод, чтобы удалить факультет");
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isPresent()) {
            facultyRepository.deleteById(id);
            faculty.get();
            return;
        }
        log.warn("Факультет по id {} не был найден для удаления", id);
        throw new FacultyNotFoundException();
    }

    @Override
    public Collection<Faculty> getAllFaculty() {
        log.info("Был вызван метод, чтобы получить список всех факультетов");
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> getAllFacultyByColor(String color) {
        log.info("Был вызван метод, чтобы найти факультеты по цвету");
        return facultyRepository.getAllFacultyByColor(color);
    }

    @Override
    public Collection<Faculty> getAllFacultyByColorOrName(String color, String name) {
        log.info("Был вызван метод, чтобы найти факультет по цвету или имени");
        return facultyRepository.getAllFacultyByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }

    @Override
    public Collection<Student> findStudentsByFacultyId(long id) {
        log.info("Был вызван метод, чтобы получить всех студентов факультета по его id");
        return studentRepository.findByFaculty_Id(id);
    }
}
