package ro.fasttrackit.course.loader;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ro.fasttrackit.course.entity.Course;
import ro.fasttrackit.course.repository.CourseRepository;

import java.util.List;

import static java.util.UUID.randomUUID;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final CourseRepository repository;

    @Override
    public void run(String... args) throws Exception {
        repository.deleteAll();
        List<Course> courses = List.of(
                new Course(randomUUID().toString(), "Programming", "Programming Course"),
                new Course(randomUUID().toString(), "Math", "Math Course"),
                new Course(randomUUID().toString(), "English", "English Course"),
                new Course(randomUUID().toString(), "German", "German Course"),
                new Course(randomUUID().toString(), "Management", "Management Course")

        );
        courses.forEach(repository::save);
        System.out.println(repository.findAll());

    }
}
