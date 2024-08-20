package com.avitam.fantasy11.validation;

import com.avitam.fantasy11.form.NotificationForm;
import com.avitam.fantasy11.form.PlayerRoleForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PlayerRoleFormValidator implements Validator {

    private static final String PLAYER_ROLE_CANNOT_BE_NULL = "Player role cannot be null!";

    @Override
    public boolean supports(Class<?> clazz) {
        return PlayerRoleForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PlayerRoleForm form = (PlayerRoleForm) target;
        if (StringUtils.isEmpty(form.getPlayerRole())) {
            errors.reject(PLAYER_ROLE_CANNOT_BE_NULL);
        }
    }
}
