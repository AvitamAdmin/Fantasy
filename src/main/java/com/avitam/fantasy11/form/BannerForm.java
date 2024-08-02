package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class BannerForm extends BaseForm {

    private String url;
    private String size;
    private MultipartFile image;
    private String pic;
}


