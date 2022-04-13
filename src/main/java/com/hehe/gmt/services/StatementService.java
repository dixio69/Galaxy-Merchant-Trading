package com.hehe.gmt.services;

import com.hehe.gmt.base.BaseEntity;
import com.hehe.gmt.base.BaseSentenceProcessor;
import com.hehe.gmt.entities.Dictionary;
import com.hehe.gmt.entities.Statement;
import com.hehe.gmt.exceptions.UnknownWordException;
import com.hehe.gmt.exceptions.UnregisteredDictionaryException;
import com.hehe.gmt.utils.RomanConverter;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class StatementService extends BaseSentenceProcessor {
    private String[] words;
    private String ROMAN_DIVIDER;
    private double ARABIC_DIVIDER;
    private String ITEM;
    private double BASE_VALUE;
    private String UNIT;
    private int sentenceDelimiterIndex;

    @Override
    public boolean isValidSentence() {
        words = sentence.split(" ");
        boolean isValidSentence = sentence.contains(SENTENCE_DELIMITER)
                && !sentence.contains("?")
                && isContainsNumber();

        if (isValidSentence)
            sentenceDelimiterIndex = sentenceDelimiterIndex();
        if (sentenceDelimiterIndex < 2 || words.length < sentenceDelimiterIndex + 3)
            return false;
        return isValidSentence;
    }

    private boolean isContainsNumber() {
        for (String s : words) {
            if (StringUtils.isNumeric(s))
                return true;
        }
        return false;
    }

    private int sentenceDelimiterIndex() {
        for (int i = 0; i < words.length; i++) {
            if (SENTENCE_DELIMITER.equals(words[i])) {
                return i;
            }
        }
        return -1;
    }

    private void setDictionaryStatement() throws UnregisteredDictionaryException {
        ROMAN_DIVIDER = "";
        Dictionary dictionary;
        for (int i = 0; i <= sentenceDelimiterIndex - 2; i++) {
            dictionary = services.getDictionaryRepository().findByKeyword(words[i], sessionId);
            if (dictionary != null) {
                ROMAN_DIVIDER += dictionary.getLetter();
            } else throw new UnregisteredDictionaryException(words[i]);
        }
    }

    @Override
    public void process() throws UnknownWordException {
        setDictionaryStatement();
        ITEM = words[sentenceDelimiterIndex - 1];
        BASE_VALUE = Long.parseLong(words[sentenceDelimiterIndex + 1]);
        UNIT = words[sentenceDelimiterIndex + 2];
        ARABIC_DIVIDER = RomanConverter.romanToAlphabetNumber(ROMAN_DIVIDER);

        double basePrice = BASE_VALUE / ARABIC_DIVIDER;

        save(Statement.builder()
                .unit(UNIT)
                .item(ITEM)
                .value(new BigDecimal(basePrice))
                .build());
    }

    @Override
    protected <T extends BaseEntity> T save(T t) {
        t = super.save(t);
        var data = (Statement) t;
        var existingData = services.getStatementRepository().findByUnitItem(data.getUnit(), data.getItem(), sessionId);
        if (existingData!=null)
            data.setId(existingData.getId());
        return (T) services.getStatementRepository().save(data);
    }
}
