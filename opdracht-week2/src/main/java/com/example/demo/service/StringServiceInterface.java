package com.example.demo.service;

import com.example.demo.repository.StringRepository;
import org.springframework.stereotype.Component;

@Component
public interface StringServiceInterface {
    StringRepository GetStringRepository();

    String reverse(String value);

    default Integer countWords(String value){
        var stringRepository = GetStringRepository();

        var countendWords = stringRepository.getCountedWords(value);
        if( countendWords == null ) {
            countendWords = value.split(" ").length;
            stringRepository.saveCountendWords(value, countendWords);
            return stringRepository.getCountedWords(value);
        }
        return countendWords;
    }
}
