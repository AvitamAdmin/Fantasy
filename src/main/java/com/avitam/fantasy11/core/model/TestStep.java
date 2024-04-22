package com.avitam.fantasy11.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "TestStep")
@Getter
@Setter
public class TestStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String className;
    private String methodName;
    private String enumName;
}
