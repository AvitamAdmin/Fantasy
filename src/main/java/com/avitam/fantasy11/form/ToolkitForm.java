package com.avitam.fantasy11.form;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ToolkitForm implements Serializable {
    private String skus;
    private List<String> sites;
    private List<String> shortcuts;
    private String voucherCode;
    private String timeZone;
    private String errorType;
    private String errorMsg;
    private String skus2;
    private String category;
    private Boolean bundle;
    private String currentPage;
    private String ssoDate;
}
