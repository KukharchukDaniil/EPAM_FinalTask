package com.epam.validator;

import java.util.regex.Matcher;

public class PasswordValidator implements Validator {
    @Override
    public boolean validate(String password) {
        return password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!?@#$%]).{6,18}$");
    }
}
