package com.avitam.fantasy11.form;

import com.avitam.fantasy11.core.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserForm implements Serializable {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String passwordConfirm;
    ;
    private Set<Role> roles;
    private String creator;
    private Boolean status;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date creationTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date lastModified;
    private Locale locale;
}
