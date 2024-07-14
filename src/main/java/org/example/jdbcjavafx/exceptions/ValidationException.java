package org.example.jdbcjavafx.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {

    private Map<String, String> errors = new HashMap<String, String>();


    public ValidationException(String message) {
        super(message);
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void addError(String key, String value) {
        errors.put(key, value);
    }

}
