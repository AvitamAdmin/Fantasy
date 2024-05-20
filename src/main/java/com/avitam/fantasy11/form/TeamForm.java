package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
@Getter
@Setter
public class TeamForm extends BaseForm {

    private String name;
    private String shortName;
    private MultipartFile logo;
    private String pic;
}
