package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.mail.service.EMail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.File;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class EmailDto extends CommonDto{

    private String to;
    private String from;
    private String subject;
    private String content;
    private Map<String, Object> model;
    private File attachment;
}
