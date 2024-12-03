package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.MobileToken;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MobileTokenDto extends CommonDto{

    private MobileToken mobileToken;

    private String otp;

    private List<MobileToken> mobileTokenList;

    private String userName;






}
