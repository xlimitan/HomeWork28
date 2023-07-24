package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyAlreadyExistsException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private static Long COUNTER = 0L;

    private final Map<Long, Faculty> storage = new HashMap<>();

    public Faculty create(Faculty faculty) {
        Long id = faculty.getId();
        if (id != null && storage.containsKey(id)) {
            throw new FacultyAlreadyExistsException();
        }
        Long nextInt = COUNTER++;
        faculty.setId(nextInt);
        storage.put(nextInt, faculty);
        return faculty;
    }

    public Faculty update(Long id, Faculty faculty) {
        if (!storage.containsKey(id)) {
            throw new FacultyNotFoundException();
        }
        storage.put(id, faculty);
        return faculty;
    }

    public Faculty getById(Long id) {
        return storage.get(id);
    }

    public Collection<Faculty> getAll() {
        return storage.values();
    }

    public Faculty remove(Long id) {
        if (!storage.containsKey(id)) {
            throw new FacultyNotFoundException();
        }
        return storage.remove(id);
    }

    public Collection<Faculty> getAllByColor(String color) {
        return storage.values().stream()
                .filter(f -> f.getColor().equalsIgnoreCase(color))
                .collect(Collectors.toList());
    }
}
