package com.avitam.fantasy11.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("PlayerRole")

public class PlayerRole extends BaseEntity {

    private String playerRole;

}