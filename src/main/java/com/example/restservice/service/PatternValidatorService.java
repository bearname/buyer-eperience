package com.example.restservice.service;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PatternValidatorService {
    private final String EMAIL_PATTERN = "^[A-Z0-9._%+-]+[A-Z0-9._%+-]+@[A-Z0-9.-]+.[A-Z]{2,6}$";
    private final String URL_PATTERN = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

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
