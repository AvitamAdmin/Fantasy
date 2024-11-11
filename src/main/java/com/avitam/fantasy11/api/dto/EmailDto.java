package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.mail.service.EMail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class EmailDto extends CommonDto{

    private EMail email;
    private List<EMail> emailList;
}
