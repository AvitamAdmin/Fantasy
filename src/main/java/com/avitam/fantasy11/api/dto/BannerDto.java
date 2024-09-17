package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.Banner;
import com.avitam.fantasy11.model.BannerSize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class BannerDto extends CommonDto{

    private Banner banner;
    private List<Banner> bannerList;
    private BannerSize bannerSize;

}
