package com.hehe.gmt.repositories;

import com.hehe.gmt.entities.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Long> {

    @Query("SELECT x FROM Statement x " +
            "WHERE x.unit = :unit " +
            "AND x.sessionId = :sessionId")
    Statement findByUnit(String unit, String sessionId);

    @Query("SELECT x FROM Statement x " +
            "WHERE x.unit = :unit " +
            "AND x.item = :item " +
            "AND x.sessionId = :sessionId")
    Statement findByUnitItem(String unit, String item, String sessionId);

    @Query("SELECT x FROM Statement x " +
            "WHERE x.item = :item " +
            "AND x.sessionId = :sessionId")
    Statement findByItem(String item, String sessionId);

    void deleteBySessionId(String sessionId);
}
