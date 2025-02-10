package com.avitam.fantasy11.api.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class WebsiteSettingsWsDto extends CommonWsDto{

    private List<WebsiteSettingDto> websiteSettingDtoList;
}
