package com.example.restservice.app.service;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PatternValidatorImpl implements PatternValidator{
    private final String EMAIL_PATTERN = "^[A-Z0-9._%+-]+[A-Z0-9._%+-]+@[A-Z0-9.-]+.[A-Z]{2,6}$";
    private final String URL_PATTERN = "^(https)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    @Override
    public boolean isValid(final String string, final ValidateType type) {
        String result = "";
        if (type == ValidateType.EMAIL) {
            result = EMAIL_PATTERN;
        } else if (type == ValidateType.URL) {
            result = URL_PATTERN;
        }

        final Pattern pattern = Pattern.compile(result, Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
}
