package com.hehe.gmt.repositories;

import com.hehe.gmt.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT x FROM Question x " +
            "WHERE x.sessionId = :sessionId " +
            "ORDER BY x.index ASC")
    List<Question> findAllBySessionId(String sessionId);

    void deleteBySessionId(String sessionId);
}
