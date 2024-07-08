package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class NotificationForm extends BaseForm{

    private String message;
    private String mobileNumber;

}
