package com.avitam.fantasy11.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "ParamInput")
@Getter
@Setter
public class ParamInput extends CommonQaFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String paramKey;
    private String paramValue;
}
