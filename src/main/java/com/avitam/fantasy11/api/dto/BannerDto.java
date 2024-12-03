package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.Banner;
import com.avitam.fantasy11.model.BannerSize;
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

public class BannerDto extends CommonDto{
    private String url;
    private String size;
    private Banner banner;
    private BannerSize bannerSize;
    private MultipartFile image;
}
