package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PlayerDto extends CommonDto{

    private MultipartFile logo;
    private String dob;
    private String nationality;
    private String teamId;
    private String playerRoleId;

}
