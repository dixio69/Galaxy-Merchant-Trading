package com.hehe.gmt.services;

import com.hehe.gmt.base.BaseSentenceProcessor;
import com.hehe.gmt.exceptions.UnknownWordException;
import com.hehe.gmt.repositories.DictionaryRepository;
import com.hehe.gmt.repositories.QuestionRepository;
import com.hehe.gmt.repositories.StatementRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class GmtService {

    @Autowired
    private DictionaryRepository dictionaryRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private StatementRepository statementRepository;

    @Transactional
    public List<String> processSentences(String sessionId, String... inputs) {
        removeAllBySessionid(sessionId);
        List<StatementService> statementServices = new ArrayList<>();
        List<QuestionService> questionServices = new ArrayList<>();
        List<String> answer = new ArrayList<>();
        int questionIndex = 0;
        BaseSentenceProcessor sentenceProcessor;

        BaseSentenceProcessor.Services services = BaseSentenceProcessor.Services.builder()
                .questionRepository(questionRepository)
                .statementRepository(statementRepository)
                .dictionaryRepository(dictionaryRepository)
                .build();
        for (String s : inputs) {
            sentenceProcessor = new DictionaryService();
            sentenceProcessor.setSessionId(sessionId);
            sentenceProcessor.setServices(services);
            sentenceProcessor.setSentence(s);
            if (!sentenceProcessor.isValidSentence()) {
                sentenceProcessor = new StatementService();
                sentenceProcessor.setSessionId(sessionId);
                sentenceProcessor.setServices(services);
                sentenceProcessor.setSentence(s);
                if (sentenceProcessor.isValidSentence()) {
                    statementServices.add((StatementService) sentenceProcessor);
                } else {
                    sentenceProcessor = new QuestionService();
                    sentenceProcessor.setSessionId(sessionId);
                    sentenceProcessor.setServices(services);
                    sentenceProcessor.setSentence(s);
                    if (sentenceProcessor.isValidSentence()) {
                        ((QuestionService) sentenceProcessor).setIndex(questionIndex);
                        questionServices.add((QuestionService) sentenceProcessor);
                        questionIndex++;
                    }else {
                        answer.add(unknownSentenceHandler());
                    }
                }
            }
        }

        for (StatementService ss :
                statementServices) {
            try {
                ss.process();
            } catch (UnknownWordException e) {
                answer.add(unknownSentenceHandler());
            }
        }

        var statements = statementRepository.findAll();
        System.out.println(statements);

        for (QuestionService qs :
                questionServices) {
            try {
                qs.process();
            } catch (UnknownWordException e) {
                answer.add(unknownSentenceHandler());
            }
        }

        questionRepository.findAllBySessionId(sessionId).stream().forEach(d->{
            answer.add(d.getAnswer());
        });

        return answer;
    }

    private void removeAllBySessionid(String sessionId){
        questionRepository.deleteBySessionId(sessionId);
        dictionaryRepository.deleteBySessionId(sessionId);
        statementRepository.deleteBySessionId(sessionId);
    }

    private String unknownSentenceHandler(){
        return "I have no idea what you are talking about";
    }
}
