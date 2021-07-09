package com.epam.ftask.validator;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class PasswordValidatorTest {
    private static final String INVALID_STRING = "az!";
    private static final String VALID_STRING = "az!!AzA@@@";

    @Test
    void testValidateShouldReturnTrueOnCorrectString() {

        //given
        PasswordValidator passwordValidator = new PasswordValidator();
        //when
        Boolean expected = passwordValidator.validate(VALID_STRING);

        //then

        Assert.assertTrue(expected);
    }

    @Test
    void testValidateShouldReturnFalseOnInvalidString() {

        //given
        PasswordValidator passwordValidator = new PasswordValidator();
        //when
        Boolean expected = passwordValidator.validate(INVALID_STRING);

        //then

        Assert.assertFalse(expected);
    }

}