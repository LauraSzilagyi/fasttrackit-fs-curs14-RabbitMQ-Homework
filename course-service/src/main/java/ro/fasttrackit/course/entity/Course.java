package ro.fasttrackit.course.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document(collection = "courses")
public record Course(
        @Id
        String id,
        String discipline,
        String description
) {
}
