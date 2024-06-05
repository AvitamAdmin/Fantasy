package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter

public class KYCForm extends BaseForm{

    private String panNumber;
    private String userIds;
    private MultipartFile panImage;
}
