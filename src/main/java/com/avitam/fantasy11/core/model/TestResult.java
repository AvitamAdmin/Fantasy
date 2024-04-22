package com.avitam.fantasy11.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TestResult")
@Setter
@Getter
@ToString
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sessionId;
    private String testName;
    private String productUrls;
    private String status = "Failed";
    private String orderNumber;
    private String timeStamp;
    private String reportFilePath;
}
