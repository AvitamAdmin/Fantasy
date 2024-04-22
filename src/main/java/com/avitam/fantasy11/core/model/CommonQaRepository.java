package com.avitam.fantasy11.core.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CommonQaRepository<T extends CommonQaFields> extends JpaRepository<T, Long> {
    T findByIdentifier(String identifier);
}
