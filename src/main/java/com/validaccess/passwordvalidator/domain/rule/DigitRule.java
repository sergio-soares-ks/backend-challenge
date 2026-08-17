package com.validaccess.passwordvalidator.domain.rule;

import org.springframework.stereotype.Component;

import com.validaccess.passwordvalidator.domain.PasswordRule;

/**
 * Requires at least one ASCII digit (0-9).
 */
@Component
public class DigitRule implements PasswordRule {

    @Override
    public boolean isSatisfiedBy(String password) {
        return password.chars().anyMatch(c -> c >= '0' && c <= '9');
    }
}
