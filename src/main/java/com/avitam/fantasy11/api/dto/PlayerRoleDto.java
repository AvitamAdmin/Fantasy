package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.PlayerRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PlayerRoleDto extends CommonDto{

    private PlayerRole playerRole;
    private List<PlayerRole> playerRoleList;
}
