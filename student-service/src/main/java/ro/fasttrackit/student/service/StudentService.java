package ro.fasttrackit.student.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.fasttrackit.student.entity.Student;
import ro.fasttrackit.student.exceptions.InvalidModelException;
import ro.fasttrackit.student.filter.StudentFilter;
import ro.fasttrackit.student.model.StudentModel;
import ro.fasttrackit.student.rabbit.MessagePublisher;
import ro.fasttrackit.student.repository.StudentDao;
import ro.fasttrackit.student.repository.StudentRepository;

import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository repository;
    private final StudentDao dao;
    private final MessagePublisher messagePublisher;

    public Page<Student> getAll(StudentFilter filters, Pageable pageable) {
        return dao.findAll(filters, pageable);
    }

    public Student addNewStudent(StudentModel newStudent) {
        validateModel(newStudent);
        Student student = Student.builder()
                .id(UUID.randomUUID().toString())
                .name(newStudent.name())
                .age(newStudent.age())
                .build();
        return repository.save(student);
    }

    private void validateModel(StudentModel model) {
        if (isNull(model.name()) || isNull(model.age()) || model.age() <= 0) {
            throw new InvalidModelException("Must contains age higher than 0 and name!");
        }
    }

    public Optional<Student> getStudentById(String id) {
        return repository.findById(id);
    }

    public Optional<Student> deleteStudentById(String id) {
        Optional<Student> student = repository.findById(id);
        student.ifPresent(s -> {
            repository.delete(s);
            messagePublisher.publishDeleteStudentById(s.id());
        });
        return student;
    }
}
