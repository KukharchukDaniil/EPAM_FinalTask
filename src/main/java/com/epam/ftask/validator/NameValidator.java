package com.epam.ftask.validator;

public class NameValidator implements Validator{
    @Override
    public boolean validate(String name) {
        return name.matches("^[A-Z](a-z).{0,30}");
    }
}
