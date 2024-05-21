package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SportTypeForm extends BaseForm{

    private String name;
    private MultipartFile logo;
}
