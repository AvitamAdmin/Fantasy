package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class AddressForm extends BaseForm{

    private String mobileNumber;
    private String line_1;
    private String line_2;
    private String city;
    private String state;
    private String pinCode;



}
