package com.validaccess.passwordvalidator.domain.rule;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SpecialCharacterRuleTest {

    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-+";

    private final SpecialCharacterRule rule = new SpecialCharacterRule();

    @Test
    void rejectsPasswordWithoutAnySpecialCharacter() {
        assertThat(rule.isSatisfiedBy("abcABC123")).isFalse();
    }

    @Test
    void rejectsEmptyPassword() {
        assertThat(rule.isSatisfiedBy("")).isFalse();
    }

    @Test
    void acceptsEachSupportedSpecialCharacter() {
        for (char specialChar : SPECIAL_CHARACTERS.toCharArray()) {
            String password = "abcABC123" + specialChar;
            assertThat(rule.isSatisfiedBy(password))
                    .as("special character '%s' should satisfy the rule", specialChar)
                    .isTrue();
        }
    }

    @Test
    void rejectsCharacterOutsideTheSupportedSet() {
        assertThat(rule.isSatisfiedBy("abcABC123_")).isFalse();
    }
}
