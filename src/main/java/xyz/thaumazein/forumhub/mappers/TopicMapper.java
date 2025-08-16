package xyz.thaumazein.forumhub.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import xyz.thaumazein.forumhub.dtos.CreateTopicRequest;
import xyz.thaumazein.forumhub.dtos.TopicDto;
import xyz.thaumazein.forumhub.dtos.UpdateTopicRequest;
import xyz.thaumazein.forumhub.entities.Topic;

@Mapper(componentModel = "spring")
public interface TopicMapper {

    void update(UpdateTopicRequest updateTopicRequest, @MappingTarget Topic topic);

    Topic toEntity(CreateTopicRequest request);

    @Mapping(source = "user.name", target = "user")
    @Mapping(source = "course.name", target = "course")
    TopicDto toDto(Topic topic);
}
