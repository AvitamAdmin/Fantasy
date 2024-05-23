package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.nio.channels.MulticastChannel;

@Getter
@Setter
public class TeamForm extends BaseForm{

    private String name;
    private String shortName;
    private MultipartFile logo;
}
