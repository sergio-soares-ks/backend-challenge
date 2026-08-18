package com.validaccess.passwordvalidator.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.validaccess.passwordvalidator.api.dto.PasswordValidationRequest;
import com.validaccess.passwordvalidator.api.dto.PasswordValidationResponse;
import com.validaccess.passwordvalidator.domain.PasswordValidator;

/**
 * Exposes password validation as an HTTP resource. The password travels in
 * the request body (never in the URL or query string) so it does not end
 * up recorded in access logs, browser history or proxy logs. For the same
 * reason, the password value itself is never logged - only the outcome.
 */
@RestController
@RequestMapping("/api/v1/password-validations")
public class PasswordController {

    private static final Logger log = LoggerFactory.getLogger(PasswordController.class);

    private final PasswordValidator passwordValidator;

    public PasswordController(PasswordValidator passwordValidator) {
        this.passwordValidator = passwordValidator;
    }

    @PostMapping
    public ResponseEntity<PasswordValidationResponse> validate(@RequestBody(required = false) PasswordValidationRequest request) {
        String password = request == null ? null : request.password();
        boolean valid = passwordValidator.isValid(password);
        log.info("Password validation completed: valid={}", valid);
        return ResponseEntity.ok(new PasswordValidationResponse(valid));
    }
}
