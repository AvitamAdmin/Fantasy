package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SEOForm extends BaseForm{

    private MultipartFile image;
    private String title;
    private String tag;
    private String description;
    public String pic;

    }

