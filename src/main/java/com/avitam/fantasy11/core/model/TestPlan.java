package com.avitam.fantasy11.core.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "TestPlan")
@Getter
@Setter
@NoArgsConstructor
public class TestPlan extends CommonQaFields {
    @OneToMany(cascade = CascadeType.ALL)
    private List<TestStep> testSteps;
}
