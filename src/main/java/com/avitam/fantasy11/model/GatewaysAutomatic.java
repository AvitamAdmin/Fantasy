package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("GatewaysAutomatic")
public class GatewaysAutomatic extends BaseEntity {

    private String gateway;
    private String gatewayStatus;
    private Binary logo;

}
