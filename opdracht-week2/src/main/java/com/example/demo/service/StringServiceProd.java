package com.example.demo.service;

import com.example.demo.repository.StringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class StringServiceProd implements StringServiceInterface {

    @Autowired
    private StringRepository stringRepository;

    public StringRepository GetStringRepository(){
        return stringRepository;
    }

    public String reverse(String value){
        return new StringBuilder(value)
                .reverse()
                .toString()
                .toUpperCase();
    }
}
