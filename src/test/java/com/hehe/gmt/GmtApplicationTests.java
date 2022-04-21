package com.hehe.gmt;

import com.hehe.gmt.base.BaseSentenceProcessor;
import com.hehe.gmt.entities.Dictionary;
import com.hehe.gmt.entities.Question;
import com.hehe.gmt.exceptions.UnknownWordException;
import com.hehe.gmt.repositories.DictionaryRepository;
import com.hehe.gmt.repositories.QuestionRepository;
import com.hehe.gmt.repositories.StatementRepository;
import com.hehe.gmt.services.DictionaryService;
import com.hehe.gmt.services.QuestionService;
import com.hehe.gmt.services.StatementService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GmtApplicationTests {
    @Autowired
    private DictionaryRepository dictionaryRepository;
    @Autowired
    private StatementRepository statementRepository;
    @Autowired
    private QuestionRepository questionRepository;

    private String SESSION_ID = "Test";
    private String DICTIONARY = "glip";
    private String DICTIONARY_ROMAN = "I";
    private BigDecimal DICTIONARY_NUMERAL = new BigDecimal(50);
    private String SENTENCE_ITEM = "silver";
    private BigDecimal SENTENCE_VALUE = new BigDecimal("40");
    private String UNIT = "credits";

    @Before
    public void loadDictionary() throws UnknownWordException {
        BaseSentenceProcessor.Services services = BaseSentenceProcessor.Services.builder()
                .dictionaryRepository(dictionaryRepository)
                .statementRepository(statementRepository)
                .questionRepository(questionRepository)
                .build();
        BaseSentenceProcessor sentenceProcessor = new DictionaryService();
        sentenceProcessor.setServices(services);
        sentenceProcessor.setSessionId(SESSION_ID);
        sentenceProcessor.setSentence(DICTIONARY + " is " + DICTIONARY_ROMAN);
        sentenceProcessor.isValidSentence();

        sentenceProcessor = new StatementService();
        sentenceProcessor.setServices(services);
        sentenceProcessor.setSessionId(SESSION_ID);
        sentenceProcessor.setSentence(DICTIONARY + " " + SENTENCE_ITEM + " is " + SENTENCE_VALUE.toString() + " " + UNIT);
        if (sentenceProcessor.isValidSentence()) {
            sentenceProcessor.process();
        }
    }

    @Test
    public void dictionaryTest() {
        var dictionary = dictionaryRepository.findByLetter(DICTIONARY_ROMAN, SESSION_ID);
        Assertions.assertThat(dictionary.getKeyword().equals(DICTIONARY));
        dictionary = dictionaryRepository.findByKeyword(DICTIONARY, SESSION_ID);
        assert (dictionary.getLetter().equals(DICTIONARY_ROMAN));
    }

    @Test
    public void sentenceTest() throws UnknownWordException {
        var sentence = statementRepository.findByItem(SENTENCE_ITEM, SESSION_ID);
        assert (sentence.getValue().compareTo(SENTENCE_VALUE.divide(DICTIONARY_NUMERAL)) == 0);
    }

    @Test
    public void questionTest() throws UnknownWordException {
        BaseSentenceProcessor.Services services = BaseSentenceProcessor.Services.builder()
                .dictionaryRepository(dictionaryRepository)
                .statementRepository(statementRepository)
                .questionRepository(questionRepository)
                .build();
        BaseSentenceProcessor questionProcessor = new QuestionService();
        questionProcessor.setServices(services);
        questionProcessor.setSessionId(SESSION_ID);
        questionProcessor.setSentence("how much is " + DICTIONARY + "?");
        if (questionProcessor.isValidSentence())
            questionProcessor.process();

        var x = questionRepository.findAll();
        questionProcessor = new QuestionService();
        questionProcessor.setServices(services);
        questionProcessor.setSessionId(SESSION_ID);
        questionProcessor.setSentence("how many " + UNIT + " is " + " glip glip glip glip"+ " " + SENTENCE_ITEM + "?");
        if (questionProcessor.isValidSentence())
            questionProcessor.process();

        x = questionRepository.findAll();
        System.out.println(x);
    }

}
