package xyz.thaumazein.forumhub.dtos;

import java.time.LocalDate;

public record TopicDto(
        Long id,
        String title,
        String message,
        LocalDate createdAt,
        String status,
        String user,
        String course
) {
}
