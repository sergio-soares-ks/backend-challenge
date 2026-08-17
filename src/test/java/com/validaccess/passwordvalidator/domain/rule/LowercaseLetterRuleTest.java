package com.validaccess.passwordvalidator.domain.rule;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LowercaseLetterRuleTest {

    private final LowercaseLetterRule rule = new LowercaseLetterRule();

    @ParameterizedTest
    @CsvSource({
            "'', false",
            "ABC123!, false",
            "ABc123!, true",
            "a, true",
    })
    void requiresAtLeastOneLowercaseLetter(String password, boolean expected) {
        assertThat(rule.isSatisfiedBy(password)).isEqualTo(expected);
    }
}
