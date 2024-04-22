package com.avitam.fantasy11.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Subsidiary")
@Getter
@Setter
@NoArgsConstructor
public class Subsidiary extends CommonFields{
    private String subId;
    private String cluster;
    private String shortDescription;
    private String isoCode;
    private int languageId;
}
