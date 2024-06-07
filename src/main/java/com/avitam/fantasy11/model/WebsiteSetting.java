package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Document("WebsiteSetting")
@Getter
@Setter
@NoArgsConstructor
public class WebsiteSetting extends BaseEntity implements Serializable {

    private Binary logoUrl;
    private Binary faviconUrl;
    private String pic2;
}
