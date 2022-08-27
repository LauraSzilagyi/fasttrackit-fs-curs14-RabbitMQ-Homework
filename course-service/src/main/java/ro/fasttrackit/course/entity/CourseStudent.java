package ro.fasttrackit.course.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document(collection = "courseStudent")
public record CourseStudent(
        @Id
        String id,
        String courseId,
        String studentId,
        Integer grade
) {
}
