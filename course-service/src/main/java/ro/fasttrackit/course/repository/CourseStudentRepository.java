package ro.fasttrackit.course.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.fasttrackit.course.entity.CourseStudent;

import java.util.List;

public interface CourseStudentRepository extends MongoRepository<CourseStudent, String> {
    List<CourseStudent> findAllByStudentId(String studentId);

    List<CourseStudent> findAllByCourseId(String courseId);
}
