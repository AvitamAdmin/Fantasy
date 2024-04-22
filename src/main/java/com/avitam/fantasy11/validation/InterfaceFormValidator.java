package com.avitam.fantasy11.validation;

import com.avitam.fantasy11.form.InterfaceForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class InterfaceFormValidator implements Validator {
    private static final String INTERFACE_ID_CANNOT_BE_NULL = "Path cannot be null!";

    @Override
    public boolean supports(Class<?> clazz) {
        return InterfaceForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        InterfaceForm form = (InterfaceForm) target;
        if (StringUtils.isEmpty(form.getPath())) {
            errors.reject(INTERFACE_ID_CANNOT_BE_NULL);
        }
    }
}
