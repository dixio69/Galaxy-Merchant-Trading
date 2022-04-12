package com.hehe.gmt.services;

import com.hehe.gmt.base.BaseEntity;
import com.hehe.gmt.base.BaseSentenceProcessor;
import com.hehe.gmt.entities.Dictionary;
import com.hehe.gmt.enumerations.RomanEnum;
import org.springframework.stereotype.Service;

public class DictionaryService extends BaseSentenceProcessor {
    private RomanEnum roman = null;
    private String[] words;

    @Override
    public boolean isValidSentence() {
        words = sentence.split(" ");
        if (words.length == 3)
            roman = RomanEnum.getByLetter(words[2]);
        boolean isValidSentence = sentence.contains("is")
                && !sentence.contains("?")
                && words.length == 3
                && roman != null;
        if (isValidSentence) {
            process();
        }

        return isValidSentence;
    }

    @Override
    public void process() {
        save(Dictionary.builder()
                .keyword(words[0])
                .letter(roman.name())
                .value(roman.getValue())
                .build());
    }

    @Override
    protected <T extends BaseEntity> void save(T t) {
        services.getDictionaryRepository().save((Dictionary) t);
    }
}
