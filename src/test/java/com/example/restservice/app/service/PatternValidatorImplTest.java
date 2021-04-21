package com.example.restservice.app.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PatternValidatorImplTest {

    private final PatternValidatorImpl patternValidator = new PatternValidatorImpl();

    @Test
    void whenEmptyEmailString() {
        assertFalse(patternValidator.isValid("", ValidateType.EMAIL));
    }

    @Test
    void whenNotEmptyNotValidEmailString() {
        assertFalse(patternValidator.isValid("asjdf", ValidateType.EMAIL));
    }

    @Test
    void whenNotHaveAtSignSymbol() {
        assertFalse(patternValidator.isValid("test.google.com", ValidateType.EMAIL));
    }

    @Test
    void whenValidEmail() {
        assertTrue(patternValidator.isValid("test@google.com", ValidateType.EMAIL));
    }

    @Test
    void whenValidEmail1() {
        assertTrue(patternValidator.isValid("ya.mikushoff@gmail.com", ValidateType.EMAIL));
    }

    @Test
    void whenNotValidUrl() {
        assertFalse(patternValidator.isValid("test@google.com", ValidateType.URL));
    }

    @Test
    void whenNotHaveProtocol() {
        assertFalse(patternValidator.isValid("google.com", ValidateType.URL));
    }

    @Test
    void whenInValid() {
        assertFalse(patternValidator.isValid("https:/google.com", ValidateType.URL));
    }

    @Test
    void whenInValid1() {
        assertFalse(patternValidator.isValid("https//google.com", ValidateType.URL));
    }

    @Test
    void whenInValid2() {
        assertFalse(patternValidator.isValid("https:google.com", ValidateType.URL));
    }

    @Test
    void whenRequestParameterValid() {
        assertTrue(patternValidator.isValid("https://google.com?search=pixel5", ValidateType.URL));
    }

    @Test
    void whenUrlHavePort() {
        assertTrue(patternValidator.isValid("https://google.com:8080", ValidateType.URL));
    }
}