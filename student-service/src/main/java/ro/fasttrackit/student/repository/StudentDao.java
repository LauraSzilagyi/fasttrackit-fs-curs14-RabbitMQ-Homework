package ro.fasttrackit.student.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.student.entity.Student;
import ro.fasttrackit.student.filter.StudentFilter;

import java.util.List;

import static java.util.Optional.ofNullable;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.support.PageableExecutionUtils.getPage;

@Repository
@RequiredArgsConstructor
public class StudentDao {
    private final MongoTemplate mongo;

    public Page<Student> findAll(StudentFilter filters, Pageable pageable) {
        Criteria criteria = new Criteria();

        ofNullable(filters.name())
                .ifPresent(name -> criteria.and("name").is(name));
        ofNullable(filters.age())
                .ifPresent(age -> criteria.and("age").is(age));
        ofNullable(filters.id())
                .ifPresent(id -> criteria.and("id").is(id));
        ofNullable(filters.minAge())
                .ifPresent(minAge -> criteria.and("age").gte(minAge));
        ofNullable(filters.maxAge())
                .ifPresent(maxAge -> criteria.and("age").lte(maxAge));


        Query query = query(criteria).with(pageable);
        List<Student> content = mongo.find(query, Student.class);
        return getPage(content, pageable, () -> mongo.count(query(criteria), Student.class));
    }
}
