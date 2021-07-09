package com.epam.ftask.validator;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class UsernameValidatorTest {
    private static final String INVALID_STRING = "az!";
    private static final String VALID_STRING = "azlalala";

    @Test
    void testValidateShouldReturnTrueOnCorrectString() {

        //given
        UsernameValidator usernameValidator = new UsernameValidator();
        //when
        Boolean expected = usernameValidator.validate(VALID_STRING);

        //then

        Assert.assertTrue(expected);
    }

    @Test
    void testValidateShouldReturnFalseOnInvalidString() {

        //given
        UsernameValidator usernameValidator = new UsernameValidator();
        //when
        Boolean expected = usernameValidator.validate(INVALID_STRING);

        //then

        Assert.assertFalse(expected);
    }

}