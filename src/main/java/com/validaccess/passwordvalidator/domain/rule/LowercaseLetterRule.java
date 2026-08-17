package com.validaccess.passwordvalidator.domain.rule;

import org.springframework.stereotype.Component;

import com.validaccess.passwordvalidator.domain.PasswordRule;

/**
 * Requires at least one ASCII lowercase letter (a-z).
 */
@Component
public class LowercaseLetterRule implements PasswordRule {

    @Override
    public boolean isSatisfiedBy(String password) {
        return password.chars().anyMatch(c -> c >= 'a' && c <= 'z');
    }
}
