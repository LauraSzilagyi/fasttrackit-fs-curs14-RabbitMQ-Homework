package ro.fasttrackit.student.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.fasttrackit.student.entity.Student;

public interface StudentRepository extends MongoRepository<Student, String> {
}
