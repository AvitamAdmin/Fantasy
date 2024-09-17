package com.avitam.fantasy11.tokenGeneration;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest {
    private String username;
    private String password;
}
