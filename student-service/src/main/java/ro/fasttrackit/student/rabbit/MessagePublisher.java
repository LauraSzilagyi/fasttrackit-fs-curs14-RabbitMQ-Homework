package ro.fasttrackit.student.rabbit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ro.fasttrackit.student.rabbit.dto.StudentRabbitDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessagePublisher {
    private final FanoutExchange fanoutExchange;
    private final RabbitTemplate rabbit;

    public void publishDeleteStudentById(String studentId) {
        log.info("publishing on fanout %s".formatted(studentId));
        rabbit.convertAndSend(fanoutExchange.getName(), "student-delete", new StudentRabbitDto(studentId));
    }
}
