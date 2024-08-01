package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class BannerForm extends BaseForm implements Serializable {

    private MultipartFile logo;
    private MultipartFile favicon;
    private String pic2;
}


