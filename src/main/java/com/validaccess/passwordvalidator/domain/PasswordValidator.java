package com.validaccess.passwordvalidator.domain;

/**
 * Application-facing port for password validation.
 * Kept as an interface so the web layer depends on an abstraction,
 * not on the concrete rule-evaluation strategy (Dependency Inversion).
 */
public interface PasswordValidator {

    boolean isValid(String password);
}
