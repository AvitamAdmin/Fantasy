package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.Notification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class NotificationDto extends CommonDto{

    private String message;
    private String mobileNumber;
}
