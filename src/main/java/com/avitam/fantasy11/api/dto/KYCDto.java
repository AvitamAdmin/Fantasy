package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.KYC;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class KYCDto extends CommonDto{

    private KYC kyc;
    private List<KYC> kycList;

}
