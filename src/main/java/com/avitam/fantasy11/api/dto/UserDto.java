package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDto extends CommonDto{
    private User user;
    private List<User> usersList;
    private String email;
    private String otp;
}
