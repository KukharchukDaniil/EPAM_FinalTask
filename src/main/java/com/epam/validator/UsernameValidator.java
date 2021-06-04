package com.epam.validator;

public class UsernameValidator implements Validator{
    @Override
    public boolean validate(String username) {
        return username.matches("^(?=.*[a-z])(?=.*[\\d]*)(?=.*[_]*).{6,18}");
    }
}
