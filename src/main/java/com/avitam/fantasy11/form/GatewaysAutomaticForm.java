package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class GatewaysAutomaticForm extends BaseForm{

    private String gateway;
    private String gatewayStatus;
    private MultipartFile logo;
}
