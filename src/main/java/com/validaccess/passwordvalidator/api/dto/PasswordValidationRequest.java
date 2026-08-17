package com.validaccess.passwordvalidator.api.dto;

/**
 * Request body for a password validation. {@code password} is
 * intentionally not annotated with bean-validation constraints (e.g.
 * {@code @NotBlank}): an empty or missing password is a legitimate input
 * whose "invalid" verdict must come from the business rules, not from
 * request-level rejection.
 */
public record PasswordValidationRequest(String password) {
}
