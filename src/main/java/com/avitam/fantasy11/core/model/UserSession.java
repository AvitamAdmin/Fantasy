package com.avitam.fantasy11.core.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "UserSession")
public class UserSession implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sessionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

}
