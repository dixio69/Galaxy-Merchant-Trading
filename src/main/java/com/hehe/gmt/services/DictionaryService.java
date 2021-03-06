package com.hehe.gmt.services;

import com.hehe.gmt.base.BaseEntity;
import com.hehe.gmt.base.BaseSentenceProcessor;
import com.hehe.gmt.entities.Dictionary;
import com.hehe.gmt.entities.Statement;
import com.hehe.gmt.enumerations.RomanEnum;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Log4j2
public class DictionaryService extends BaseSentenceProcessor {
    private RomanEnum roman = null;
    private String[] words;

    @Override
    public boolean isValidSentence() {
        sentence = StringUtils.normalizeSpace(sentence);
        words = sentence.split(" ");
        if (words.length == 3)
            roman = RomanEnum.getByLetter(words[2].toUpperCase());
        boolean isValidSentence = sentence.contains(SENTENCE_DELIMITER)
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
    protected <T extends BaseEntity> T save(T t) {
        log.info("Saving Dictionary "+((Dictionary)t).toString());
        t = super.save(t);
        var data = (Dictionary) t;
        var existingData = services.getDictionaryRepository().findByKeyword(data.getKeyword(), sessionId);
        if (existingData!=null)
            data.setId(existingData.getId());
        return (T) services.getDictionaryRepository().save(data);
    }
}
