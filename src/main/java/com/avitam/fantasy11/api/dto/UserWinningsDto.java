package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.UserWinnings;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserWinningsDto extends CommonDto{

    private UserWinnings userWinnings;
    private List<UserWinnings> userWinningsList;
}
