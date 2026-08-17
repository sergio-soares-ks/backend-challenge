package com.validaccess.passwordvalidator.domain.rule;

import org.springframework.stereotype.Component;

import com.validaccess.passwordvalidator.domain.PasswordRule;

/**
 * Requires at least one ASCII uppercase letter (A-Z).
 */
@Component
public class UppercaseLetterRule implements PasswordRule {

    @Override
    public boolean isSatisfiedBy(String password) {
        return password.chars().anyMatch(c -> c >= 'A' && c <= 'Z');
    }
}
