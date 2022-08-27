package ro.fasttrackit.course.model;

import lombok.Builder;

@Builder
public record StudentModel(String name, Integer age) {
}
