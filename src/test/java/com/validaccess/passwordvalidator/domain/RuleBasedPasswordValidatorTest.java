package com.validaccess.passwordvalidator.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.validaccess.passwordvalidator.domain.rule.AllowedCharactersRule;
import com.validaccess.passwordvalidator.domain.rule.DigitRule;
import com.validaccess.passwordvalidator.domain.rule.LowercaseLetterRule;
import com.validaccess.passwordvalidator.domain.rule.MinLengthRule;
import com.validaccess.passwordvalidator.domain.rule.SpecialCharacterRule;
import com.validaccess.passwordvalidator.domain.rule.UniqueCharactersRule;
import com.validaccess.passwordvalidator.domain.rule.UppercaseLetterRule;

/**
 * Wires all production rules together, mirroring exactly the examples
 * given in the challenge description.
 */
class RuleBasedPasswordValidatorTest {

    private final PasswordValidator validator = new RuleBasedPasswordValidator(List.of(
            new MinLengthRule(),
            new DigitRule(),
            new LowercaseLetterRule(),
            new UppercaseLetterRule(),
            new SpecialCharacterRule(),
            new AllowedCharactersRule(),
            new UniqueCharactersRule()
    ));

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {""})
    void treatsNullAndEmptyAsInvalid(String password) {
        assertThat(validator.isValid(password)).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
            "aa, false",
            "ab, false",
            "AAAbbbCc, false",
            "AbTp9!foo, false",
            "AbTp9!foA, false",
            "'AbTp9 fok', false",
            "AbTp9!fok, true",
    })
    void matchesChallengeExamples(String password, boolean expected) {
        assertThat(validator.isValid(password)).isEqualTo(expected);
    }
}
