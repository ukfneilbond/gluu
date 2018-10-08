package com.amido.secureia.common;

public enum PasswordValidationResult {
	
    ERROR_LENGTH ("Password must be between 8 and 32 characters in length."),

    ERROR_NO_LETTERS_OR_NUMBERS ("Password must contain at least one letter and one number."),

    ERROR_CONFIRMATION ("Password confirmation does not match."),
    
    ERROR_COMMON_PASSWORD ("Password must not be a common word."),

    OK ("");

    private String message;

    PasswordValidationResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
    
}
