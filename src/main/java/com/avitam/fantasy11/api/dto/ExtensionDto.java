package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.Extension;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ExtensionDto extends CommonDto{

    private Extension extension;
    private List<Extension> ExtensionList;


}