package com.avitam.fantasy11.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("DepositsLog")
public class DepositsLog extends BaseEntity{

    private String userId;
    private int payingAmount;
    private String gatewayName;
    private String transactionId;
    private String depositStatus;


}
