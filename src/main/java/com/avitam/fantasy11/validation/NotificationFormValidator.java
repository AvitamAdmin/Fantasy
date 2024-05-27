package com.avitam.fantasy11.validation;

import com.avitam.fantasy11.form.InterfaceForm;
import com.avitam.fantasy11.form.NotificationForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class NotificationFormValidator implements Validator {

    private static final String MESSAGE_CANNOT_BE_NULL = "Message cannot be null!";

    @Override
    public boolean supports(Class<?> clazz) {
        return NotificationForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NotificationForm form = (NotificationForm) target;
        if (StringUtils.isEmpty(form.getMessage())) {
            errors.reject(MESSAGE_CANNOT_BE_NULL);
        }
    }
}
