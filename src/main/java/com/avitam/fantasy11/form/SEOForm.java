package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
@Getter
@Setter
public class SEOForm extends BaseForm{

    private Binary image;
    private String title;
    private String tag;
    private String description;
}
