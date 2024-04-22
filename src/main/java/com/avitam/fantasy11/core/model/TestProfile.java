package com.avitam.fantasy11.core.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TestProfile")
@Getter
@Setter
@NoArgsConstructor
public class TestProfile extends CommonQaFields {

    private String testPlan;
    private Boolean enableSite;
    private Boolean isDefault;
    @Lob
    private String skus;
}
