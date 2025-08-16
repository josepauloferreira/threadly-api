package xyz.thaumazein.forumhub.dtos;

public record UpdatePasswordRequest(String oldPassword,
                                    String newPassword) {
}
