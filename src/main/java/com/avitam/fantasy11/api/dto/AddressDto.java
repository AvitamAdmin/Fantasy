package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AddressDto extends CommonDto{
    private String mobileNumber;
    private String line_1;
    private String line_2;
    private String city;
    private String state;
    private String pinCode;
}
