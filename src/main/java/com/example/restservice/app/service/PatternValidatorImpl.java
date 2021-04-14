package com.example.restservice.app.service;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PatternValidatorImpl implements PatternValidator {
    private final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$";
    private final String URL_PATTERN = "^(https)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    @Override
    public boolean isValid(final String string, final ValidateType type) {
        if (string == null) {
            return false;
        }

        String validateType = "";
        if (type == ValidateType.EMAIL) {
            validateType = EMAIL_PATTERN;
        } else if (type == ValidateType.URL) {
            validateType = URL_PATTERN;
        }

        final Pattern pattern = Pattern.compile(validateType, Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
}
