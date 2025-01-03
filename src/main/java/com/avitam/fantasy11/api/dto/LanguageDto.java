package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.Language;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LanguageDto extends CommonDto {

    private String code;
}
