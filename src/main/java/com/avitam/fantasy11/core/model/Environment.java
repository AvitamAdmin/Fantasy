package com.avitam.fantasy11.core.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Environment")
@Getter
@Setter
@NoArgsConstructor
public class Environment extends CommonQaFields {

    private String preConditionURL;
    private String hybrisUrlFormula;
    private String hybrisUrlExample;
    private String aemUrlFormula;
    private String aemUrlExample;
}

