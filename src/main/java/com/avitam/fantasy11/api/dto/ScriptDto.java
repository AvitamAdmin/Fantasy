package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.Script;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class ScriptDto extends CommonDto{
    private Script script;
}
