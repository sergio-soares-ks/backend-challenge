package com.validaccess.passwordvalidator.domain.rule;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AllowedCharactersRuleTest {

    private final AllowedCharactersRule rule = new AllowedCharactersRule();

    @Test
    void acceptsEmptyPassword() {
        assertThat(rule.isSatisfiedBy("")).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"abcABC123", "abcABC123!@#$%^&*()-+"})
    void acceptsLettersDigitsAndSupportedSpecialCharacters(String password) {
        assertThat(rule.isSatisfiedBy(password)).isTrue();
    }

    @Test
    void rejectsWhitespace() {
        assertThat(rule.isSatisfiedBy("AbTp9 fok")).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"abcABC123_", "abcABC123.", "abcABC123ç", "abc\tABC123"})
    void rejectsCharactersOutsideTheSupportedSet(String password) {
        assertThat(rule.isSatisfiedBy(password)).isFalse();
    }
}
