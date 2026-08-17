package com.validaccess.passwordvalidator.domain;

/**
 * A single, independent criterion a password may satisfy.
 * Adding a new business rule means adding a new implementation of this
 * interface (and registering it as a Spring bean) - no existing class
 * needs to change (Open/Closed Principle).
 */
public interface PasswordRule {

    boolean isSatisfiedBy(String password);
}
