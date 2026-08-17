package com.validaccess.passwordvalidator.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.validaccess.passwordvalidator.api.dto.PasswordValidationRequest;
import com.validaccess.passwordvalidator.api.dto.PasswordValidationResponse;

/**
 * End-to-end test: real Spring context, real rules, real HTTP call.
 * Exercises the exact examples from the challenge description through the
 * whole stack (controller -> validator -> rules).
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PasswordValidationApiIntegrationTest {

    private static final String ENDPOINT = "/api/v1/password-validations";

    @Autowired
    private TestRestTemplate restTemplate;

    @ParameterizedTest
    @CsvSource({
            "'', false",
            "aa, false",
            "ab, false",
            "AAAbbbCc, false",
            "AbTp9!foo, false",
            "AbTp9!foA, false",
            "'AbTp9 fok', false",
            "AbTp9!fok, true",
    })
    void validatesPasswordsThroughTheFullStack(String password, boolean expected) {
        ResponseEntity<PasswordValidationResponse> response = restTemplate.postForEntity(
                ENDPOINT, new PasswordValidationRequest(password), PasswordValidationResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().valid()).isEqualTo(expected);
    }
}
