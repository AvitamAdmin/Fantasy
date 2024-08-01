package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@Document("Banner")
public class Banner extends BaseEntity{

    private String logoUrl;
    private String faviconUrl;
    private String pic2;
}
