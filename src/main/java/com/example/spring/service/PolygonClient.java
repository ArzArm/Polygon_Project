package com.example.spring.service;

import com.example.spring.builder.PolygonUrl;
import com.example.spring.entity.EuroToUsd;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class PolygonClient {

    @Value("${url}")
    String url;
    @Value("${apiKey}")
    private String apiKey;


    public List<EuroToUsd> getConvertorList(PolygonUrl url) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(this.url + "/" + url.getCurrencyPair() + "/" + "range" + "/"
                + url.getMultiplier() + "/" + url.getTimespan() + "/" + url.getFrom() + "/" + url.getTo() + "?adjusted=" + url.getAdjusted()
                + "&sort=" + url.getSort() + "&limit=" + url.getLimit() + "&apiKey=" + apiKey, String.class);
        JSONObject jsonObject = new JSONObject(response);
        JSONArray resultsArray = new JSONArray(jsonObject.get("results").toString());
        List<EuroToUsd> euroToUsdList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        JSONObject marketData;
        for (int i = 0; i < resultsArray.length(); i++) {
            marketData = new JSONObject(resultsArray.get(i).toString());
            euroToUsdList.add(mapper.readValue(marketData.toString(), EuroToUsd.class));
        }
        return euroToUsdList;
    }


}
