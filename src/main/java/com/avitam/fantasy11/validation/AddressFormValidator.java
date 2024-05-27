package com.avitam.fantasy11.validation;

import com.avitam.fantasy11.form.AddressForm;
import com.avitam.fantasy11.form.InterfaceForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AddressFormValidator implements Validator {

    private static final String LINE_1_CANNOT_BE_NULL = "Line_1 cannot be null!";
    private static final String CITY_CANNOT_BE_NULL = "City cannot be null!";
    private static final String STATE_CANNOT_BE_NULL = "State cannot be null!";
    private static final String PIN_CODE_CANNOT_BE_NULL = "Pin_Code cannot be null!";

    @Override
    public boolean supports(Class<?> clazz) {
        return InterfaceForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AddressForm form = (AddressForm) target;
        if (StringUtils.isEmpty(form.getLine_1())) {
            errors.reject(LINE_1_CANNOT_BE_NULL);
        }
        else if (StringUtils.isEmpty(form.getCity())) {
            errors.reject(CITY_CANNOT_BE_NULL);
        }
       else if (StringUtils.isEmpty(form.getState())) {
            errors.reject(STATE_CANNOT_BE_NULL);
        }
       else if (StringUtils.isEmpty(form.getPinCode())) {
            errors.reject(PIN_CODE_CANNOT_BE_NULL);
        }

    }
}
