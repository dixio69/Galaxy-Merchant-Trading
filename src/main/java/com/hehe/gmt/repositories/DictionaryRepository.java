package com.hehe.gmt.repositories;

import com.hehe.gmt.entities.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {
    Dictionary findByLetter(String letter);
    Dictionary findByKeyword(String keyword);
}
