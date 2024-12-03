package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.KYC;
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
public class KYCDto extends CommonDto{

    private String panNumber;

    private String userId;

    private MultipartFile panImage;

}
