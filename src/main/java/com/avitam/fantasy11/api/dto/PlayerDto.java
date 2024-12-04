package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.Player;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PlayerDto extends CommonDto{

    private String dob;
    private String nationality;
    private String teamId;

    private String playerRoleId;

    private MultipartFile playerImage;
}
