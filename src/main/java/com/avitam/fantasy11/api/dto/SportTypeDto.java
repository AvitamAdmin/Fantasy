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
public class SportTypeDto extends CommonDto {
    private MultipartFile logo;
    private Integer maxPlayers;
    private Integer section1MaxPlayers;
    private Integer section2MaxPlayers;
    private Integer section3MaxPlayers;
}
