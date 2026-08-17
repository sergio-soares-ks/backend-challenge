package com.validaccess.passwordvalidator.domain.rule;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DigitRuleTest {

    private final DigitRule rule = new DigitRule();

    @ParameterizedTest
    @CsvSource({
            "'', false",
            "abcDEF!, false",
            "abcDEF9, true",
            "9, true",
    })
    void requiresAtLeastOneDigit(String password, boolean expected) {
        assertThat(rule.isSatisfiedBy(password)).isEqualTo(expected);
    }
}
