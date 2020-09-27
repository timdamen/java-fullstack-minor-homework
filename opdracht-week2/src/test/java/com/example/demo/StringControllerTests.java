package com.example.demo;

import com.example.demo.repository.StringRepository;
import com.example.demo.service.StringService;
import com.example.demo.service.StringServiceProd;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StringControllerTests {
    @Mock
    StringService stringService;
    @Mock
    StringServiceProd stringServiceProd;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void reverseTest() throws Exception {
        var word = "Lorem";
        when(stringService.reverse(word)).thenReturn("meroL");
        when(stringServiceProd.reverse(word)).thenReturn("MEROL");

        mockMvc.perform(get("/reverse-string").param("value", word))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("meroL")));

    }

    @Test
    void wordCountTest() throws Exception {
        var word = "Lorem Ipsum is slechts een proeftekst uit het drukkerij- en zetterijwezen.";
        when(stringService.countWords(word)).thenReturn(11);
        when(stringServiceProd.countWords(word)).thenReturn(11);

        mockMvc.perform(get("/count-words").param("value", word))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("11")));
    }
}
