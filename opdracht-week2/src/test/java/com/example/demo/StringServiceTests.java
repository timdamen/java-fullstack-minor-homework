package com.example.demo;

import com.example.demo.repository.StringRepository;
import com.example.demo.service.StringService;
import com.example.demo.service.StringServiceProd;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {
                StringRepository.class,
                StringService.class,
                StringServiceProd.class
        })

class StringServiceTests {
    @Mock
    StringRepository stringRepository;
    @InjectMocks
    StringService stringService;
    @InjectMocks
    StringServiceProd stringServiceProd;

    @Test
    void constructorTest(){
        assertThat(stringService.GetStringRepository()).isEqualTo(stringRepository);
    }

    @Test
    void constructorTestProd(){
        assertThat(stringServiceProd.GetStringRepository()).isEqualTo(stringRepository);
    }

    @Test
    void testWordCount() {
        // Given
        String word1 = "Lorem Ipsum is slechts een proeftekst uit het drukkerij- en zetterijwezen.";
        String word2 = "Lorem Ipsum is slechts een proeftekst uit het drukkerij- en zetterijwezen. extra";
        // When
        when(stringRepository.getCountedWords(word1)).thenReturn(null);
        when(stringRepository.getCountedWords(word2)).thenReturn(12);
        // Then
        assertThat(stringService.countWords(word1)).isEqualTo(null);
        assertThat(stringService.countWords(word2)).isEqualTo(12);
    }
}
