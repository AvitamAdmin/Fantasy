package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.PointsMaster;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PointsMasterDto extends CommonDto{

    private PointsMaster pointsMaster;
    private List<PointsMaster> pointsMasterList;
}
