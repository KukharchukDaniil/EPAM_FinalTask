package com.epam.ftask.validator;

public class PasswordValidator implements Validator {
    @Override
    public boolean validate(String password) {
        return password.matches("^(?=.*[a-z!?@#$%A-Z\\d]).{6,18}$");
    }
}
