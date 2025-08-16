package xyz.thaumazein.forumhub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.thaumazein.forumhub.entities.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    boolean existsByTitleAndMessage(String title, String message);
}