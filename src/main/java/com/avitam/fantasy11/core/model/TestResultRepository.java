package com.avitam.fantasy11.core.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    @Query("FROM TestResult WHERE sessionId = ?1 AND testName = ?2")
    List<TestResult> findAllBySessionIdAndTestName(String sessionId, String testName);
}
