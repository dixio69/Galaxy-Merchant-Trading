package com.hehe.gmt;

import com.hehe.gmt.base.BaseSentenceProcessor;
import com.hehe.gmt.entities.Dictionary;
import com.hehe.gmt.repositories.DictionaryRepository;
import com.hehe.gmt.services.DictionaryService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GmtApplicationTests {
    @Autowired
    private DictionaryRepository dictionaryRepository;

    @Before
    public void loadDictionary(){
        BaseSentenceProcessor.Services services = BaseSentenceProcessor.Services.builder()
                .dictionaryRepository(dictionaryRepository)
                .build();
        BaseSentenceProcessor sentenceProcessor = new DictionaryService();
        sentenceProcessor.setServices(services);
        sentenceProcessor.setSentence("glup is L");
        sentenceProcessor.isValidSentence();
    }

    @Test
    public void dictionaryTest() {
        Dictionary dictionary = dictionaryRepository.findByLetter("L");
        Assertions.assertThat(dictionary.getKeyword().equals("glup"));
        dictionary = dictionaryRepository.findByKeyword("glup");
        assert(dictionary.getLetter().equals("L"));
    }

}
