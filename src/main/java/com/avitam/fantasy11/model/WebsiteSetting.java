package com.avitam.fantasy11.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "WebsiteSetting")
@Getter
@Setter
@NoArgsConstructor
public class WebsiteSetting extends CommonFields {
    private String logoUrl;
    private String name = "Touchmind Suite";
    private String faviconUrl;
}
