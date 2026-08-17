package com.validaccess.passwordvalidator.domain.rule;

/**
 * Single source of truth for which characters count as "special", shared
 * by {@link SpecialCharacterRule} and {@link AllowedCharactersRule} so the
 * accepted set only ever needs to change in one place.
 */
final class SpecialCharacters {

    static final String CHARACTERS = "!@#$%^&*()-+";

    private SpecialCharacters() {
    }

    static boolean contains(char c) {
        return CHARACTERS.indexOf(c) >= 0;
    }
}
