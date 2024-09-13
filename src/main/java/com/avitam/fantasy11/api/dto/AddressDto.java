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

    private Address address;
    private List<Address> addressList;
}
