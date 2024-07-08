package com.avitam.fantasy11.validation;

import com.avitam.fantasy11.form.RoleForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class RoleFormValidator implements Validator {

    private static final String USER_ROLE_NAME_SHOULD_START_WITH_ROLE = "Name cannot be empty";

    @Override
    public boolean supports(Class<?> clazz) {

        return RoleForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RoleForm form = (RoleForm) target;

        if (StringUtils.isEmpty(form.getName())) {
            errors.reject(USER_ROLE_NAME_SHOULD_START_WITH_ROLE);
        }
    }
}
