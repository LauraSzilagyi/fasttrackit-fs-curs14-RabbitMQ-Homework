package ro.fasttrackit.course.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String id) {
        super("Entity with id %s doesn't exist".formatted(id));
    }
}
