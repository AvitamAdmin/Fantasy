package com.avitam.fantasy11.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "WebsiteSetting")
@Getter
@Setter
@NoArgsConstructor
public class WebsiteSetting extends BaseEntity implements Serializable {

    private String logoUrl;
    private String faviconUrl;
}
