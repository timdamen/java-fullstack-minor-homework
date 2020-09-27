package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class StringRepository {

    private final Map<String, Integer> wordCount;

    public StringRepository(){
        wordCount = new HashMap<>();
    }

    public Integer getCountedWords(String text) {
        if(!wordCount.containsKey(text)){
            return null;
        }
        return wordCount.get(text);
    }

    public void saveCountendWords(String text, Integer count) {
        if(!wordCount.containsKey(text)){
            wordCount.put(text, count);
        }
    }
}