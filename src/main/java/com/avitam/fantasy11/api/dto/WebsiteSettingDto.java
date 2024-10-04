package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.WebsiteSetting;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class WebsiteSettingDto extends CommonDto{

    private WebsiteSetting websiteSetting;

    private List<WebsiteSetting> websiteSettingList;
}
