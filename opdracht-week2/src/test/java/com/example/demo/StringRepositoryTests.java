package com.example.demo;

import com.example.demo.repository.StringRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {StringRepository.class})
class StringRepositoryTests {
    @Autowired
    private StringRepository stringRepository;

    @Test
    void saveCountendWordsTest(){
        // Given
        var words = "Lorem Ipsum is slechts een proeftekst uit het drukkerij- en zetterijwezen.";
        // When
        stringRepository.saveCountendWords(words, 11);
        // Then
        assertThat(stringRepository.getCountedWords(words)).isEqualTo(11);
    }

    @Test
    void getCountedWordsTest(){
        // Given
        var words1 = "Lorem Ipsum is slechts een proeftekst uit het drukkerij- en zetterijwezen.";
        var words2 = "Lorem Ipsum is slechts een proeftekst uit het drukkerij- en zetterijwezen. extra";
        stringRepository.saveCountendWords(words2, 12);
        // When
        var counted1 = stringRepository.getCountedWords(words1);
        var counted2 = stringRepository.getCountedWords(words2);
        // Then
        assertThat(counted1).isEqualTo(null);
        assertThat(counted2).isEqualTo(12);
    }

}