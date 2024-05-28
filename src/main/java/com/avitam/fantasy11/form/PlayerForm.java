package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PlayerForm extends BaseForm{

    private String playerName;
    private String teamId;
    private MultipartFile playerImage;
    private String playerRoleId;
}
