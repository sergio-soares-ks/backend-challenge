package com.validaccess.passwordvalidator.domain.rule;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class UppercaseLetterRuleTest {

    private final UppercaseLetterRule rule = new UppercaseLetterRule();

    @ParameterizedTest
    @CsvSource({
            "'', false",
            "abc123!, false",
            "abC123!, true",
            "A, true",
    })
    void requiresAtLeastOneUppercaseLetter(String password, boolean expected) {
        assertThat(rule.isSatisfiedBy(password)).isEqualTo(expected);
    }
}
