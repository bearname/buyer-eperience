package com.example.restservice.app.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PatternValidatorImplTest {

    private final PatternValidatorImpl patternValidator = new PatternValidatorImpl();

    @Test
    public void whenEmptyEmailString() {
        assertFalse(patternValidator.isValid("", ValidateType.EMAIL));
    }

    @Test
    public void whenNotEmptyNotValidEmailString() {
        assertFalse(patternValidator.isValid("asjdf", ValidateType.EMAIL));
    }

    @Test
    public void whenNotHaveAtSignSymbol() {
        assertFalse(patternValidator.isValid("test.google.com", ValidateType.EMAIL));
    }

    @Test
    public void whenValidEmail() {
        assertTrue(patternValidator.isValid("test@google.com", ValidateType.EMAIL));
    }


    @Test
    public void whenNotValidUrl() {
        assertFalse(patternValidator.isValid("test@google.com", ValidateType.URL));
    }

    @Test
    public void whenNotHaveProtocol() {
        assertFalse(patternValidator.isValid("google.com", ValidateType.URL));
    }

    @Test
    public void whenInValid() {
        assertFalse(patternValidator.isValid("https:/google.com", ValidateType.URL));
    }

    @Test
    public void whenInValid1() {
        assertFalse(patternValidator.isValid("https//google.com", ValidateType.URL));
    }

    @Test
    public void whenInValid2() {
        assertFalse(patternValidator.isValid("https:google.com", ValidateType.URL));
    }



    @Test
    public void whenRequestParameterValid() {
        assertTrue(patternValidator.isValid("https://google.com?search=pixel5", ValidateType.URL));
    }

    @Test
    public void whenUrlHavePort() {
        assertTrue(patternValidator.isValid("https://google.com:8080", ValidateType.URL));
    }
}