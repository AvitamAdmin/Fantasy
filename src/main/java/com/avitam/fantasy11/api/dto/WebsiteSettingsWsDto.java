package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class WebsiteSettingsWsDto extends CommonWsDto {

    private List<WebsiteSettingDto> websiteSettingDtoList;
}
