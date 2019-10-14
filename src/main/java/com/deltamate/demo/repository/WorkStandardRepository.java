package com.deltamate.demo.repository;

import com.deltamate.demo.model.WorkStandard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkStandardRepository extends JpaRepository<WorkStandard, Long> {
}
