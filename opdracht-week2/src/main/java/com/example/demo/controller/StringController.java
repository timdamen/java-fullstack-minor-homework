package com.example.demo.controller;

import com.example.demo.service.StringServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StringController {

    @Autowired
    private StringServiceInterface stringService;

    @GetMapping("/reverse-string")
    public String reverse(@RequestParam(value="value") String value)  {
        return stringService.reverse(value);
    }

    @GetMapping("/count-words")
    public Integer countWords(@RequestParam(value="value") String value)  {
        return stringService.countWords(value);
    }
}
