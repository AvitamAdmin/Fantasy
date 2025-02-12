package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AddressDto extends CommonDto {
    private String mobileNumber;
    private String line_1;
    private String line_2;
    private String city;
    private String state;
    private String pinCode;
}
