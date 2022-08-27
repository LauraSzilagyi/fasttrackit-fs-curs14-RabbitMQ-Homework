package ro.fasttrackit.course.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.course.entity.Course;
import ro.fasttrackit.course.entity.CourseStudent;
import ro.fasttrackit.course.exceptions.EntityNotFoundException;
import ro.fasttrackit.course.model.CourseModel;
import ro.fasttrackit.course.model.StudentModel;
import ro.fasttrackit.course.service.CourseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("courses")
public class CourseController {
    private final CourseService service;

    @GetMapping()
    List<Course> getAllCourses(@RequestParam(required = false) String studentId) {
        return service.getAllCourses(studentId);
    }

    @PostMapping
    Course addNewCourse(@RequestBody CourseModel model) {
        return service.addNewCourse(model);
    }

    @GetMapping("{id}")
    Course getCourseById(@PathVariable String id) {
        return service.getCourseById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @GetMapping("{courseId}/students")
    List<StudentModel> getStudentsForThisCourse(@PathVariable String courseId) {
        return service.getStudentsForThisCourse(courseId);
    }

    @PostMapping("{courseId}/students/{studentId}")
    CourseStudent postAStudentToACourse(@PathVariable String courseId,
                                        @PathVariable String studentId,
                                        @RequestParam Integer grade) {
        return service.postAStudentToACourse(courseId, studentId, grade);
    }

}
