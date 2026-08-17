package com.validaccess.passwordvalidator.api;

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
 * up recorded in access logs, browser history or proxy logs.
 */
@RestController
@RequestMapping("/api/v1/password-validations")
public class PasswordController {

    private final PasswordValidator passwordValidator;

    public PasswordController(PasswordValidator passwordValidator) {
        this.passwordValidator = passwordValidator;
    }

    @PostMapping
    public ResponseEntity<PasswordValidationResponse> validate(@RequestBody(required = false) PasswordValidationRequest request) {
        String password = request == null ? null : request.password();
        boolean valid = passwordValidator.isValid(password);
        return ResponseEntity.ok(new PasswordValidationResponse(valid));
    }
}
