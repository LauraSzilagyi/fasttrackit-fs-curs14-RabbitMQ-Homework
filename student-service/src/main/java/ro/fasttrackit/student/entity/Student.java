package ro.fasttrackit.student.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document(collection = "students")
public record Student(
        @Id
        String id,
        String name,
        Integer age) {
}
