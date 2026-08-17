package com.validaccess.passwordvalidator.api;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.validaccess.passwordvalidator.domain.PasswordValidator;

/**
 * Verifies the HTTP contract in isolation: the controller correctly wires
 * the request/response payloads to the {@link PasswordValidator} port,
 * which is mocked here so this test does not depend on rule behaviour.
 */
@WebMvcTest(PasswordController.class)
class PasswordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PasswordValidator passwordValidator;

    @Test
    void returnsValidTrueWhenValidatorAcceptsThePassword() throws Exception {
        given(passwordValidator.isValid(eq("AbTp9!fok"))).willReturn(true);

        mockMvc.perform(post("/api/v1/password-validations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\":\"AbTp9!fok\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid").value(true));
    }

    @Test
    void returnsValidFalseWhenValidatorRejectsThePassword() throws Exception {
        given(passwordValidator.isValid(eq("aa"))).willReturn(false);

        mockMvc.perform(post("/api/v1/password-validations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\":\"aa\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid").value(false));
    }

    @Test
    void treatsMissingPasswordFieldAsNull() throws Exception {
        given(passwordValidator.isValid(eq((String) null))).willReturn(false);

        mockMvc.perform(post("/api/v1/password-validations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid").value(false));
    }

    @Test
    void returnsBadRequestForMalformedJson() throws Exception {
        mockMvc.perform(post("/api/v1/password-validations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("not-json"))
                .andExpect(status().isBadRequest());
    }
}
