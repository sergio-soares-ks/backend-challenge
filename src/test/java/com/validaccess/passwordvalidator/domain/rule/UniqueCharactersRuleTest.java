package com.validaccess.passwordvalidator.domain.rule;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class UniqueCharactersRuleTest {

    private final UniqueCharactersRule rule = new UniqueCharactersRule();

    @ParameterizedTest
    @CsvSource({
            "'', true",
            "abc, true",
            "aa, false",
            "AbTp9!foo, false",
            "AbTp9!foA, false",
            "AbTp9!fok, true",
    })
    void detectsRepeatedCharactersCaseSensitively(String password, boolean expected) {
        assertThat(rule.isSatisfiedBy(password)).isEqualTo(expected);
    }
}
