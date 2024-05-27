package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SportTypeForm extends BaseEntityForm{

    private String sportName;
    private MultipartFile logo;
}
