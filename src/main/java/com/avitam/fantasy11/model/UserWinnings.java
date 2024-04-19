package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("UserWinnings")
public class UserWinnings extends BaseEntity{

    private  int userId;

    private int matchId;

    private int teamId;

    private double winningAmount;
}
