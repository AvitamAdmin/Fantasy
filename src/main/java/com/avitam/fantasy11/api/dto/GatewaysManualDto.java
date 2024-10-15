package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.GatewaysManual;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GatewaysManualDto extends CommonDto{

    private GatewaysManual gatewaysManual;
    private List<GatewaysManual> gatewaysManualList;
    private MultipartFile logo;
}
