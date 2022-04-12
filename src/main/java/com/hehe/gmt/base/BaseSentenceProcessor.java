package com.hehe.gmt.base;

import com.hehe.gmt.repositories.DictionaryRepository;
import lombok.Builder;
import lombok.Data;

public abstract class BaseSentenceProcessor {
    @Data
    @Builder
    public static class Services{
        private DictionaryRepository dictionaryRepository;
    }

    protected Services services;
    protected String sentence;

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public void setServices(Services services) {
        this.services = services;
    }

    public abstract boolean isValidSentence();
    public abstract void process();
    protected abstract <T extends BaseEntity> void save(T t);
}
