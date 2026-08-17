package com.validaccess.passwordvalidator.domain.rule;

import org.springframework.stereotype.Component;

import com.validaccess.passwordvalidator.domain.PasswordRule;

/**
 * Restricts every character to letters, digits, or one of the accepted
 * special characters. This is what keeps whitespace (and any other
 * symbol outside the accepted set) out of a valid password, rather than
 * relying on it merely failing to satisfy the other rules.
 */
@Component
public class AllowedCharactersRule implements PasswordRule {

    @Override
    public boolean isSatisfiedBy(String password) {
        return password.chars().allMatch(this::isAllowed);
    }

    private boolean isAllowed(int c) {
        return (c >= 'a' && c <= 'z')
                || (c >= 'A' && c <= 'Z')
                || (c >= '0' && c <= '9')
                || SpecialCharacters.contains((char) c);
    }
}
