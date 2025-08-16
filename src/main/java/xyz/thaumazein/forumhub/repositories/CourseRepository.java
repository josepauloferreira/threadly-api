package xyz.thaumazein.forumhub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.thaumazein.forumhub.entities.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}