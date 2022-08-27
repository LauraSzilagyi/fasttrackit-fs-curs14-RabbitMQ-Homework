package ro.fasttrackit.student.loader;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ro.fasttrackit.student.entity.Student;
import ro.fasttrackit.student.repository.StudentRepository;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final StudentRepository repository;


    @Override
    public void run(String... args) throws Exception {
        repository.deleteAll();
        List.of(
                new Student(UUID.randomUUID().toString(), "Mihai", 12),
                new Student(UUID.randomUUID().toString(), "Maria", 10),
                new Student(UUID.randomUUID().toString(), "Andrei", 13),
                new Student(UUID.randomUUID().toString(), "Andrea", 9),
                new Student(UUID.randomUUID().toString(), "George", 12)

        ).forEach(repository::save);
        System.out.println(repository.findAll());
    }
}
