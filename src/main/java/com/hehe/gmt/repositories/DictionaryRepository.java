package com.hehe.gmt.repositories;

import com.hehe.gmt.entities.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {
    @Query("SELECT x FROM Dictionary x " +
            "WHERE x.letter = :letter " +
            "AND x.sessionId = :sessionId")
    Dictionary findByLetter(String letter, String sessionId);

    @Query("SELECT x FROM Dictionary x " +
            "WHERE x.keyword = :keyword " +
            "AND x.sessionId = :sessionId")
    Dictionary findByKeyword(String keyword, String sessionId);

    void deleteBySessionId(String sessionId);
}
