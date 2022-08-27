package ro.fasttrackit.course.rabbit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ro.fasttrackit.course.entity.CourseStudent;
import ro.fasttrackit.course.rabbit.dto.StudentRabbitDTO;
import ro.fasttrackit.course.repository.CourseStudentRepository;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageListener {

    private final CourseStudentRepository repository;

    @RabbitListener(queues = "#{fanoutQueue.name}")
    void receiveFanout(StudentRabbitDTO student) {
        log.info("Student courses have to be deleted for studentId: %s".formatted(student.studentId()));
        List<CourseStudent> allStudentCourses = repository.findAllByStudentId(student.studentId());
        repository.deleteAll(allStudentCourses);
    }
}
