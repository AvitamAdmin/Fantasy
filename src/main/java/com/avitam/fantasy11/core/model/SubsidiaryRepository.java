package com.avitam.fantasy11.core.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubsidiaryRepository extends JpaRepository<Subsidiary, Long> {
}
