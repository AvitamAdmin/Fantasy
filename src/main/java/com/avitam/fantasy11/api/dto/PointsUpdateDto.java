package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.PointsUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PointsUpdateDto extends CommonDto{

    private PointsUpdate pointsUpdate;
    private List<PointsUpdate> pointsUpdateList;
}
