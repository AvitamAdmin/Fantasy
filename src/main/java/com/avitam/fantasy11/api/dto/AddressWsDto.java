package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.Address;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AddressWsDto extends CommonWsDto{
    private Address address;
    private List<AddressDto> addressDtoList;
}
