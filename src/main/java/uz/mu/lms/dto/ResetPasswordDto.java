package uz.mu.lms.dto;

import lombok.Builder;

@Builder
public record ResetPasswordDto (String password, String passwordConfirmation) {
}

