package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.SEO;
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
public class SEODto extends CommonDto {


    private String title;
    private String tag;
    private String description;

    private MultipartFile image;
}
