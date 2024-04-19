package com.avitam.fantasy11;

import com.avitam.fantasy11.model.BaseEntity;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@Document("Player")
public class Player extends BaseEntity {

    private String name;

    private  int teamId;

    private MultipartFile playerImage;

    private int playerRoleId;
}
