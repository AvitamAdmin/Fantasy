package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class ExtensionForm extends BaseForm{

    private String pic;
    private String extendStatus;
    private MultipartFile image;
}
