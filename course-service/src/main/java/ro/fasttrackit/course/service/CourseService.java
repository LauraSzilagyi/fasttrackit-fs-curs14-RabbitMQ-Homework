package ro.fasttrackit.course.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ro.fasttrackit.course.entity.Course;
import ro.fasttrackit.course.entity.CourseStudent;
import ro.fasttrackit.course.exceptions.EntityNotFoundException;
import ro.fasttrackit.course.exceptions.InvalidModelException;
import ro.fasttrackit.course.model.CourseModel;
import ro.fasttrackit.course.model.StudentModel;
import ro.fasttrackit.course.repository.CourseRepository;
import ro.fasttrackit.course.repository.CourseStudentRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository repository;
    private final CourseStudentRepository courseStudentRepository;
    private final RestTemplate restTemplate;


    public List<Course> getAllCourses(String studentId) {
        if (nonNull(studentId)) {
            List<CourseStudent> courseStudents = getCourseStudentsByStudentId(studentId);
            return courseStudents.stream()
                    .map(CourseStudent::courseId)
                    .map(this::getCoursesById)
                    .toList();
        } else {
            return repository.findAll();
        }
    }

    private List<CourseStudent> getCourseStudentsByStudentId(String studentId) {
        return courseStudentRepository.findAllByStudentId(studentId);
    }

    private Course getCoursesById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    public Course addNewCourse(CourseModel model) {
        validateModel(model);
        Course newCourse = Course.builder()
                .id(randomUUID().toString())
                .discipline(model.discipline())
                .description(model.description())
                .build();
        return repository.save(newCourse);
    }

    private void validateModel(CourseModel model) {
        if (isBlank(model.discipline()) || isBlank(model.description())) {
            throw new InvalidModelException("Must contains discipline and description!");
        }
    }

    public Optional<Course> getCourseById(String id) {
        return repository.findById(id);
    }

    public CourseStudent postAStudentToACourse(String courseId, String studentId, Integer grade) {
        repository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(courseId));


        String url = buildGetStudentByIdUrl(studentId);
        try {
            restTemplate.getForObject(url, StudentModel.class);
        } catch (HttpClientErrorException e) {
            log.info(e.getMessage());
            throw new EntityNotFoundException(studentId);
        }


        CourseStudent entity = CourseStudent.builder()
                .id(randomUUID().toString())
                .courseId(courseId)
                .studentId(studentId)
                .grade(grade)
                .build();
        return courseStudentRepository.save(entity);
    }

    public List<StudentModel> getStudentsForThisCourse(String courseId) {
        List<CourseStudent> courseStudents = courseStudentRepository.findAllByCourseId(courseId);

        return courseStudents.stream()
                .map(this::mapToStudentModel)
                .filter(student -> nonNull(student.name()) && nonNull(student.age()))
                .toList();
    }

    private StudentModel mapToStudentModel(CourseStudent courseStudent) {
        try {
            String url = buildGetStudentByIdUrl(courseStudent.studentId());
            return restTemplate.getForObject(url, StudentModel.class);
        } catch (HttpClientErrorException | ResourceAccessException e) {
            return StudentModel.builder().build();
        }
    }

    private String buildGetStudentByIdUrl(String studentId) {
        return UriComponentsBuilder
                .fromHttpUrl("http://localhost:8181/students")
                .pathSegment(studentId)
                .toUriString();
    }
}
