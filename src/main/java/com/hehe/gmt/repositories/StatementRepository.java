package com.hehe.gmt.repositories;

import com.hehe.gmt.entities.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Long> {
    Statement findByUnit(String unit);
    Statement findByItem(String item);
}
