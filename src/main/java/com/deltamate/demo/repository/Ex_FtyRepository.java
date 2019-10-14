package com.deltamate.demo.repository;

import com.deltamate.demo.model.Ex_Fty;
import com.deltamate.demo.model.StyleTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface Ex_FtyRepository extends JpaRepository<Ex_Fty, Long> {

    @Transactional
    @Modifying
    @Query(value = "delete from Ex_Fty ex where ex.style_fk = :style_fk")
    void deleteByStyle_fk(@Param("style_fk") StyleTable style_fk);
}
