package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
public class PlayerForm extends BaseForm{

    private String dob;
    private String nationality;
    private String teamId;
    private MultipartFile playerImage;
    private String playerRoleId;
    private String pic;

}