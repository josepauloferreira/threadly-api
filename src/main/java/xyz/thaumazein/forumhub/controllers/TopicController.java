package xyz.thaumazein.forumhub.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import xyz.thaumazein.forumhub.dtos.CreateTopicRequest;
import xyz.thaumazein.forumhub.dtos.TopicDto;
import xyz.thaumazein.forumhub.dtos.UpdateTopicRequest;
import xyz.thaumazein.forumhub.mappers.TopicMapper;
import xyz.thaumazein.forumhub.repositories.CourseRepository;
import xyz.thaumazein.forumhub.repositories.TopicRepository;
import xyz.thaumazein.forumhub.repositories.UserRepository;

import java.util.Map;

@RestController
@RequestMapping("/topics")
@AllArgsConstructor
public class TopicController {

    private final TopicRepository topicRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    TopicMapper topicMapper;

    @PostMapping
    public ResponseEntity<?> createTopic(@RequestBody @Valid CreateTopicRequest createTopicRequest,
                                         UriComponentsBuilder uriBuilder) {

        if (topicRepository.existsByTitleAndMessage(createTopicRequest.title(), createTopicRequest.message())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("Topic", "Topic is already register"));
        }

        var topic = topicMapper.toEntity(createTopicRequest);

        var course = courseRepository.findById(createTopicRequest.courseId()).orElse(null);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }

        var user = userRepository.findById(createTopicRequest.userId()).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        topic.setUser(user);
        topic.setCourse(course);

        topicRepository.save(topic);

        var createTopicResponse = topicMapper.toDto(topic);
        var uri = uriBuilder.path("/topics/{id}").buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(uri).body(createTopicResponse);
    }

    @GetMapping
    public Page<TopicDto> getAllTopics(@PageableDefault(sort = {"createdAt"}) Pageable pageable) {
        return topicRepository.findAll(pageable).map(topic -> topicMapper.toDto(topic));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDto> getTopic(@PathVariable Long id) {
        var topic = topicRepository.findById(id).orElse(null);
        if (topic == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(topicMapper.toDto(topic));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        var topic = topicRepository.findById(id).orElse(null);
        if (topic == null) {
            return ResponseEntity.notFound().build();
        }

        topicRepository.delete(topic);
        return ResponseEntity.noContent().build();

    }

    @PutMapping("{id}")
    public ResponseEntity<TopicDto> updateTopic(@PathVariable Long id, @RequestBody UpdateTopicRequest updateTopicRequest) {
        var topic = topicRepository.findById(id).orElse(null);
        if (topic == null) {
            return ResponseEntity.notFound().build();
        }

        topicMapper.update(updateTopicRequest, topic);
        topicRepository.save(topic);

        return ResponseEntity.ok(topicMapper.toDto(topic));

    }
}
