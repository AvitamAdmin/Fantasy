package com.avitam.fantasy11.api.dto;

import java.util.List;
import jdk.jfr.SettingDefinition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class NotificationWsDto extends CommonWsDto{
    private List<NotificationDto> notificationDtoList;
}
