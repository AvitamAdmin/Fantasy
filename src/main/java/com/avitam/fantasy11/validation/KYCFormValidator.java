package com.avitam.fantasy11.validation;

import com.avitam.fantasy11.form.KYCForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KYCFormValidator implements Validator {

    private static final String UserId="UserId Must Not be Null";
    private static final String PanNumber="PanNumber Must Not be Null";
    private static final String PanImage="PanImage Must Not be Null";
    private static final String PanFormat1="Pan Number Must be 'ABCDE1234F' Like This Format only";


    @Override
    public boolean supports(Class<?> clazz) {

        return KYCForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        KYCForm form = (KYCForm) target;
        String PanNumbers=form.getPanNumber();
        if (StringUtils.isEmpty(form.getUserId())) {
            errors.reject(UserId);
        } else if (StringUtils.isEmpty(form.getPanNumber())) {
            errors.reject(PanNumber);
        }
            String regex = "^[A-Z]{5}[0-9]{4}[A-Z]$";
            Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(PanNumbers);
        if (!matcher.matches()) {
            errors.reject(PanNumbers,PanFormat1);
        }

    }
}
