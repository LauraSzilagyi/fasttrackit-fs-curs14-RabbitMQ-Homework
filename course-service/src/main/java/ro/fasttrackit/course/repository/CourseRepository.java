package ro.fasttrackit.course.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.fasttrackit.course.entity.Course;

public interface CourseRepository extends MongoRepository<Course, String> {
}
