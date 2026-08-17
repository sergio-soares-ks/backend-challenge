package com.validaccess.passwordvalidator.domain.rule;

import org.springframework.stereotype.Component;

import com.validaccess.passwordvalidator.domain.PasswordRule;

/**
 * Requires at least one character from {@value SpecialCharacters#CHARACTERS}.
 */
@Component
public class SpecialCharacterRule implements PasswordRule {

    @Override
    public boolean isSatisfiedBy(String password) {
        return password.chars().anyMatch(c -> SpecialCharacters.contains((char) c));
    }
}
