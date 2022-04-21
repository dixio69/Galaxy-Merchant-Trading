package com.hehe.gmt.base;

import com.hehe.gmt.entities.Question;
import com.hehe.gmt.exceptions.UnknownWordException;
import com.hehe.gmt.repositories.DictionaryRepository;
import com.hehe.gmt.repositories.QuestionRepository;
import com.hehe.gmt.repositories.StatementRepository;
import com.hehe.gmt.services.GmtService;
import com.hehe.gmt.services.QuestionService;
import lombok.Builder;
import lombok.Data;

public abstract class BaseSentenceProcessor {
    @Data
    @Builder
    public static class Services {
        private DictionaryRepository dictionaryRepository;
        private StatementRepository statementRepository;
        private QuestionRepository questionRepository;
    }

    protected Services services;
    protected String sentence;
    protected String SENTENCE_DELIMITER = "is";
    protected String sessionId;
    protected int index;

    public void setSentence(String sentence) {
        this.sentence = sentence.toLowerCase();
    }

    public void setServices(Services services) {
        this.services = services;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public abstract boolean isValidSentence();

    public abstract void process() throws UnknownWordException;

    protected <T extends BaseEntity> T save(T t) {
        t.setSessionId(sessionId);
        return t;
    }

//    public void saveUnknownSentence(){};
    public void saveUnknownSentence() {
        Question question = Question.builder()
                .index(index)
                .answer(GmtService.unknownSentenceHandler()).build();
        question.setSessionId(sessionId);
        services.getQuestionRepository().save(question);
    }
}
