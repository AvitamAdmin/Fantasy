package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class AddressForm extends BaseForm{

    private String userId;
    private String line_1;
    private String line_2;
    private String city;
    private String state;
    private String pinCode;

}
