package com.epam.validator;

public class NameValidator implements Validator{
    @Override
    public boolean validate(String name) {
        return name.matches("^(.).{0,30}");
    }
}
