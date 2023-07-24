package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentAlreadyExistsException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private static Long COUNTER = 0L;

    private final Map<Long, Student> storage = new HashMap<>();

    public Student create(Student student) {
        Long id = student.getId();
        if (id != null && storage.containsKey(id)) {
            throw new StudentAlreadyExistsException();
        }
        Long nextInt = COUNTER++;
        student.setId(nextInt);
        storage.put(nextInt, student);
        return student;
    }

    public Student update(Long id, Student student) {
        if (!storage.containsKey(id)) {
            throw new StudentNotFoundException();
        }
        storage.put(id, student);
        return student;
    }

    public Student getById(Long id) {
        return storage.get(id);
    }
    public Collection<Student> getAll(){
        return storage.values();
    }

    public Student remove(Long id){
        if (!storage.containsKey(id)) {
            throw new StudentNotFoundException();
        }
        return storage.remove(id);
    }
    public Collection<Student> getAllByAge(int age) {
        return storage.values().stream()
                .filter(f -> f.getAge() == age)
                .collect(Collectors.toList());
    }
}
