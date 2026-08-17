package com.validaccess.passwordvalidator.domain.rule;

import org.springframework.stereotype.Component;

import com.validaccess.passwordvalidator.domain.PasswordRule;

/**
 * Requires the password to have at least {@value #MIN_LENGTH} characters.
 */
@Component
public class MinLengthRule implements PasswordRule {

    static final int MIN_LENGTH = 9;

    @Override
    public boolean isSatisfiedBy(String password) {
        return password.length() >= MIN_LENGTH;
    }
}
