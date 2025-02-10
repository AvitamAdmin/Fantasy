package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("WebsiteSetting")
@Getter
@Setter
@NoArgsConstructor
public class WebsiteSetting extends CommonFields {

    private Binary logo;
    private Binary favicon;

}
