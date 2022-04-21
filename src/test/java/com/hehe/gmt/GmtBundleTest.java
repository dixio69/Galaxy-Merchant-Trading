package com.hehe.gmt;

import com.hehe.gmt.base.BaseSentenceProcessor;
import com.hehe.gmt.exceptions.UnknownWordException;
import com.hehe.gmt.repositories.DictionaryRepository;
import com.hehe.gmt.repositories.QuestionRepository;
import com.hehe.gmt.repositories.StatementRepository;
import com.hehe.gmt.services.DictionaryService;
import com.hehe.gmt.services.GmtService;
import com.hehe.gmt.services.QuestionService;
import com.hehe.gmt.services.StatementService;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class GmtBundleTest {
    @Autowired
    private GmtService gmtService;

    private String SESSION_ID = "BundleTest";

    @Test
    public void theTest() {
        List<String> answers = gmtService.processSentences(SESSION_ID,
        "glob is I",
                "prok is V",
                "pish is X",
                "tegj is L",
                "glob glob Silver is 34 Credits",
                "glob prok Gold is 57800 Credits",
                "pish pish Iron is 3910 Credits",
                "how much is pish tegj glob glob ?",
                "how many Credits is glob prok Silver ?",
                "how many Credits is glob prok Gold ?",
                "how many Credits is glob prok Iron ?",
                "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"
                );
        log.info("=====================answers=====================");
        answers.stream().forEach(d->log.info(d));
    }
}
