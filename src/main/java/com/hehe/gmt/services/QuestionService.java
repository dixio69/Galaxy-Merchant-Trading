package com.hehe.gmt.services;

import com.hehe.gmt.base.BaseEntity;
import com.hehe.gmt.base.BaseSentenceProcessor;
import com.hehe.gmt.entities.Dictionary;
import com.hehe.gmt.entities.Question;
import com.hehe.gmt.entities.Statement;
import com.hehe.gmt.exceptions.UnknownWordException;
import com.hehe.gmt.utils.RomanConverter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

@Log4j2
public class QuestionService extends BaseSentenceProcessor {
    private String[] words;
    private String ITEM;
    private String ROMAN_MULTIPLIER;
    private String MULTIPLIER_STR;
    private String UNIT;
    private String answer;
    private double ARABIC_MULTIPLIER;
    private BigDecimal BASE_PRICE;
    private int sentenceDelimiterIndex;
    private int index;

    @Override
    public boolean isValidSentence() {
        sentence = StringUtils.normalizeSpace(sentence);
        words = sentence.replace("?", "").split(" ");
        boolean isValidSentence = sentence.contains(SENTENCE_DELIMITER)
                && sentence.contains("?")
                && sentence.contains("how")
                && (sentence.contains("many") || sentence.contains("much"));

        if (isValidSentence)
            sentenceDelimiterIndex = sentenceDelimiterIndex();
        if (sentenceDelimiterIndex < 2)
            return false;
        return isValidSentence;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private int sentenceDelimiterIndex() {
        for (int i = 0; i < words.length; i++) {
            if (SENTENCE_DELIMITER.equals(words[i])) {
                return i;
            }
        }
        return -1;
    }

    private void setDictionaryStatementQuestion() throws UnknownWordException {
        ROMAN_MULTIPLIER = "";
        Dictionary dictionary;
        ITEM = "";
        Statement statement = null;
        MULTIPLIER_STR = "";
        for (int i = sentenceDelimiterIndex + 1; i < words.length - 1; i++) {
            if (i < words.length - 1) {
                log.info("Processing dictionary : " + words[i]);
                dictionary = services.getDictionaryRepository().findByKeyword(words[i], sessionId);
                if (dictionary != null) {
                    ROMAN_MULTIPLIER += dictionary.getLetter();
                    MULTIPLIER_STR += words[i] + " ";
                } else {
                    throw new UnknownWordException();
                }
            } else {
                log.info("Processing statement : " + words[i]);
                statement = services.getStatementRepository().findByItem(words[i], sessionId);
                if (statement != null && ITEM.equals("")) {
                    BASE_PRICE = statement.getValue();
                } else if (statement != null && !ITEM.equals("")) {
                    throw new UnknownWordException();
                }
            }
        }
    }

    private void setDictionaryStatementConverter() throws UnknownWordException {
        ROMAN_MULTIPLIER = "";
        MULTIPLIER_STR = "";
        Dictionary dictionary;
        for (int i = sentenceDelimiterIndex + 1; i < words.length; i++) {
            dictionary = services.getDictionaryRepository().findByKeyword(words[i], sessionId);
            if (dictionary != null) {
                ROMAN_MULTIPLIER += dictionary.getLetter();
                MULTIPLIER_STR += words[i] + " ";
            } else {
                throw new UnknownWordException();
            }
        }
    }

    private String sentenceConstructor(String... str) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length - 1; i++) {
            stringBuilder.append(str[i]).append(" ");
        }
        stringBuilder.append(str[str.length - 1]);
        return stringBuilder.toString();
    }

    @Override
    public void process() throws UnknownWordException {
        if (sentence.contains("much")) {
            setDictionaryStatementConverter();
            ARABIC_MULTIPLIER = RomanConverter.romanToAlphabetNumber(ROMAN_MULTIPLIER);
            answer = sentenceConstructor(MULTIPLIER_STR.trim(), SENTENCE_DELIMITER, new BigDecimal(ARABIC_MULTIPLIER).toBigInteger().toString() + "");
        } else if (sentence.contains("many")) {
            setDictionaryStatementQuestion();
            ARABIC_MULTIPLIER = RomanConverter.romanToAlphabetNumber(ROMAN_MULTIPLIER);
            ITEM = words[words.length - 1];
            UNIT = words[sentenceDelimiterIndex - 1];
            Statement statement = services.getStatementRepository().findByUnitItem(UNIT, ITEM, sessionId);
            BASE_PRICE = statement.getValue();

            answer = sentenceConstructor(MULTIPLIER_STR.trim(), StringUtils.capitalize(ITEM), SENTENCE_DELIMITER,
                    new BigDecimal(ARABIC_MULTIPLIER).multiply(BASE_PRICE).toBigInteger().toString(), UNIT);
        } else throw new UnknownWordException();
        save(Question.builder()
                .questionLong(ARABIC_MULTIPLIER)
                .questionStr(MULTIPLIER_STR)
                .unit(UNIT)
                .index(index)
                .answer(answer)
                .build());
    }

    @Override
    protected <T extends BaseEntity> T save(T t) {
        t = super.save(t);
        return (T) services.getQuestionRepository().save((Question) t);
    }
}
