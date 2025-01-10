package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("ContestDetails")
public class ContestDetails extends BaseEntity{
    private Double firstPrice;
    private  Float winningPercentage;
    private int maxEntries;
    private String winningId;
    private String contestId;

}
