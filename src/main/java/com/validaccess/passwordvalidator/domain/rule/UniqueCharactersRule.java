package com.validaccess.passwordvalidator.domain.rule;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.validaccess.passwordvalidator.domain.PasswordRule;

/**
 * Requires every character in the password to be distinct (case-sensitive):
 * 'A' and 'a' count as different characters.
 */
@Component
public class UniqueCharactersRule implements PasswordRule {

    @Override
    public boolean isSatisfiedBy(String password) {
        Set<Character> seen = new HashSet<>();
        for (char c : password.toCharArray()) {
            if (!seen.add(c)) {
                return false;
            }
        }
        return true;
    }
}
