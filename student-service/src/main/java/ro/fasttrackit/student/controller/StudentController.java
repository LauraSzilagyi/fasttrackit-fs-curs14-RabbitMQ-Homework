package ro.fasttrackit.student.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.student.entity.Student;
import ro.fasttrackit.student.exceptions.EntityNotFoundException;
import ro.fasttrackit.student.filter.StudentFilter;
import ro.fasttrackit.student.model.StudentModel;
import ro.fasttrackit.student.service.StudentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("students")
public class StudentController {
    private final StudentService service;

    @GetMapping
    Page<Student> getAll(StudentFilter filters, Pageable pageable) {
        return service.getAll(filters, pageable);
    }

    @PostMapping
    Student addNewStudent(@RequestBody StudentModel newStudent) {
        return service.addNewStudent(newStudent);
    }

    @GetMapping("{id}")
    Student getStudentById(@PathVariable String id) {
        return service.getStudentById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @DeleteMapping("{id}")
    Student deleteStudentById(@PathVariable String id) {
        return service.deleteStudentById(id).orElse(null);
    }

}
