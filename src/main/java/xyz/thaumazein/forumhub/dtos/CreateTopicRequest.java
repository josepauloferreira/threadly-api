package xyz.thaumazein.forumhub.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTopicRequest(
        @NotBlank(message = "Title must not be blank")
        String title,
        @NotBlank(message = "Message must not be blank")
        String message,

        @NotNull(message = "User must not be null")
        Long userId,

        @NotNull(message = "Course must not be null")
        Long courseId
) {
}
