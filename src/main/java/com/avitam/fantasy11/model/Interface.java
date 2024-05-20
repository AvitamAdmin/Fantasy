package com.avitam.fantasy11.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("interface")
@Getter
@Setter
@NoArgsConstructor
public class Interface extends BaseEntity{
    private String parentId;
    private String interfaceId;
    private String shortDescription;
}
