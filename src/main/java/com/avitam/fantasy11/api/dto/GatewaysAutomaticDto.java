package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.Address;
import com.avitam.fantasy11.model.GatewaysAutomatic;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GatewaysAutomaticDto extends CommonDto{

    private GatewaysAutomatic gatewaysAutomatic;
    private List<GatewaysAutomatic> gatewaysAutomaticList;
}