package com.validaccess.passwordvalidator.domain.rule;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MinLengthRuleTest {

    private final MinLengthRule rule = new MinLengthRule();

    @ParameterizedTest
    @CsvSource({
            "'', false",
            "aa, false",
            "12345678, false",
            "123456789, true",
            "1234567890, true",
    })
    void evaluatesLength(String password, boolean expected) {
        assertThat(rule.isSatisfiedBy(password)).isEqualTo(expected);
    }
}
