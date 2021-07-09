package com.epam.ftask.validator;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class NameValidatorTest {

    public static final String VALID_STRING = "Valid";
    public static final String INVALID_STRING = "invalidString";

    @Test
    void testValidateShouldReturnTrueOnCorrectString() {

        //given
        NameValidator nameValidator = new NameValidator();
        //when
        Boolean expected = nameValidator.validate(VALID_STRING);

        //then

        Assert.assertTrue(expected);
    }

    @Test
    void testValidateShouldReturnFalseOnInvalidString() {

        //given
        NameValidator nameValidator = new NameValidator();
        //when
        Boolean expected = nameValidator.validate(INVALID_STRING);

        //then

        Assert.assertFalse(expected);
    }
}