package ro.fasttrackit.student.filter;

public record StudentFilter(
        String name,
        Integer age,
        Integer minAge,
        Integer maxAge,
        String id) {
}
