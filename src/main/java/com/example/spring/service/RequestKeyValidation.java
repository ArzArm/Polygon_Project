package com.example.spring.service;

import java.util.Arrays;
import java.util.List;

public class RequestKeyValidation {
    private final List<String> keyList;

    public RequestKeyValidation(){
    keyList = Arrays.asList("averagePrice", "openPrice", "closePrice", "highestPrice", "lowestPrice", "date");
    }

    public boolean validation(String key){
     return keyList.contains(key);

    }
}
