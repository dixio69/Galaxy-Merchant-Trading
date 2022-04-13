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

    @Before
    public void loadDictionary() throws UnknownWordException {
        BaseSentenceProcessor.Services services = BaseSentenceProcessor.Services.builder()
                .dictionaryRepository(dictionaryRepository)
                .statementRepository(statementRepository)
                .questionRepository(questionRepository)
                .build();
        BaseSentenceProcessor sentenceProcessor = new DictionaryService();
        sentenceProcessor.setServices(services);
        sentenceProcessor.setSentence("glup is L");
        sentenceProcessor.isValidSentence();

        sentenceProcessor = new StatementService();
        sentenceProcessor.setServices(services);
        sentenceProcessor.setSentence("glup silver is 100 credits");
        if (sentenceProcessor.isValidSentence()) {
            sentenceProcessor.process();
        }
    }

    @Test
    public void dictionaryTest() {
        var dictionary = dictionaryRepository.findByLetter("L");
        Assertions.assertThat(dictionary.getKeyword().equals("glup"));
        dictionary = dictionaryRepository.findByKeyword("glup");
        assert (dictionary.getLetter().equals("L"));
    }

    @Test
    public void sentenceTest() throws UnknownWordException {
        var sentence = statementRepository.findByItem("silver");
        assert (sentence.getValue().compareTo(new BigDecimal(2)) == 0);
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
        questionProcessor.setSentence("how much is glup?");
        if (questionProcessor.isValidSentence())
            questionProcessor.process();

        var x = questionRepository.findAll();
        questionProcessor = new QuestionService();
        questionProcessor.setServices(services);
        questionProcessor.setSentence("how many credits is glup silver?");
        if (questionProcessor.isValidSentence())
            questionProcessor.process();

        x = questionRepository.findAll();
        System.out.println(x);
    }

}
