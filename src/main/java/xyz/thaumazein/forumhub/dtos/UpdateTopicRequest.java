package xyz.thaumazein.forumhub.dtos;

public record UpdateTopicRequest(
        String title,
        String message,
        String status
) {
}
