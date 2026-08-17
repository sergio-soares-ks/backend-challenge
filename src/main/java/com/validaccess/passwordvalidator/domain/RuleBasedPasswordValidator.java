package com.validaccess.passwordvalidator.domain;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * Validates a password by requiring every registered {@link PasswordRule}
 * to be satisfied. Spring injects all {@link PasswordRule} beans found in
 * the context, so the set of rules is configured purely by which
 * components exist - this class never needs to know the individual rules.
 */
@Component
public class RuleBasedPasswordValidator implements PasswordValidator {

    private final List<PasswordRule> rules;

    public RuleBasedPasswordValidator(List<PasswordRule> rules) {
        this.rules = List.copyOf(rules);
    }

    @Override
    public boolean isValid(String password) {
        if (password == null) {
            return false;
        }
        return rules.stream().allMatch(rule -> rule.isSatisfiedBy(password));
    }
}
