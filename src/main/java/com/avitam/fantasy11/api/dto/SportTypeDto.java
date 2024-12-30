package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.SportType;
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
public class SportTypeDto extends CommonDto{
    private MultipartFile image;
}
