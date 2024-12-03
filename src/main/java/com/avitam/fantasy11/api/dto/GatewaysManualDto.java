package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.GatewaysManual;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GatewaysManualDto extends CommonDto{
    private String gateway;
    private String gatewayStatus;
    private MultipartFile logo;
}
