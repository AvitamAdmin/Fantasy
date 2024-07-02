package com.avitam.billing.validation;

import com.avitam.billing.form.BillOfSupplyForm;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BillOfSupplyValidator implements Validator {
    private static final String CUSTOMER_NAME_CANNOT_BE_NULL = "Customer name cannot be null!";
    private static final String RATE_CANNOT_BE_NULL = "Rate cannot be null!";
    private static final String PARTICULARS_CANNOT_BE_NULL = "Particulars cannot be null!";
    private static final String KG_CANNOT_BE_NULL = "Kilogram cannot be null!";
    private static final String QUANTITY_CANNOT_BE_NULL = "Quantity cannot be null!";
    private static final String MOBILENUMBER_CANNOT_BE_NULL = "Mobile number cannot be null!";
    private static final String UPI_PAYMENT_CANNOT_BE_NULL = "UPI payment cannot be null!";
    private static final String CASH_PAYMENT_CANNOT_BE_NULL = "Cash payment cannot be null!";
    private static final String PENDING_AMOUNT_CANNOT_BE_NULL = "Pending amount cannot be null!";


    @Override
    public boolean supports(Class<?> clazz) {
        return BillOfSupplyForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BillOfSupplyForm form = (BillOfSupplyForm) target;
        if (StringUtils.isEmpty(form.getCustomerName())) {
            errors.reject(CUSTOMER_NAME_CANNOT_BE_NULL);
        }
        if (ObjectUtils.isEmpty(form.getRate())) {
            errors.reject(RATE_CANNOT_BE_NULL);
        }
        if (ObjectUtils.isEmpty(form.getParticulars())) {
            errors.reject(PARTICULARS_CANNOT_BE_NULL);
        }
        if (ObjectUtils.isEmpty(form.getKg())) {
            errors.reject(KG_CANNOT_BE_NULL);
        }
        if (ObjectUtils.isEmpty(form.getQuantity())) {
            errors.reject(QUANTITY_CANNOT_BE_NULL);
        }
        if (ObjectUtils.isEmpty(form.getMobileNumber())) {
            errors.reject(MOBILENUMBER_CANNOT_BE_NULL);
        }
        if (ObjectUtils.isEmpty(form.getUpiPayment())) {
            errors.reject(UPI_PAYMENT_CANNOT_BE_NULL);
        }
        if (ObjectUtils.isEmpty(form.getCashPayment())) {
            errors.reject(CASH_PAYMENT_CANNOT_BE_NULL);
        }
        if (ObjectUtils.isEmpty(form.getPendingAmount())) {
            errors.reject(PENDING_AMOUNT_CANNOT_BE_NULL);
        }
    }
}
