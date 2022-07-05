package com.example.spring.service;


import com.example.spring.builder.PolygonUrl;
import com.example.spring.builder.PolygonUrlBuilder;
import com.example.spring.entity.EuroToUsd;
import com.example.spring.repository.EuroToUsdRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ConvertorService {
    @Autowired
    EuroToUsdRepository euroToUsdRepository;
    @Autowired
    PolygonClient polygonClient;


    PolygonUrl polygonUrl = new PolygonUrlBuilder()
            .setCurrencyPair("C:EURUSD")
            .setMultiplier(1)
            .setTimespan("day")
            .setDateFrom(Date.valueOf("2021-06-22"))
            .setDateTo(Date.valueOf("2021-07-22"))
            .setAdjusted(true)
            .setSort("asc")
            .setLimit(120).build();


    public void saveAllEuroToUsd() throws JsonProcessingException {
        euroToUsdRepository.saveAll(polygonClient.getConvertorList(polygonUrl));
    }

    public List<EuroToUsd> findAllByDate(Date from, Date to, boolean flag) throws JsonProcessingException {
        if (flag || from.before(polygonUrl.getFrom()) || to.after(polygonUrl.getTo())) {
            polygonUrl.setFrom(from);
            polygonUrl.setTo(to);
            List<EuroToUsd> euroToUsdList = polygonClient.getConvertorList(polygonUrl);
            AtomicInteger i = new AtomicInteger(1);
            euroToUsdList.forEach(euroToUsd -> euroToUsd.setId(i.getAndIncrement()));

            return euroToUsdList;
        }

        return euroToUsdRepository.findAllEuroToUsdByDate(from, to);
    }

    public List<EuroToUsd> findEuroToUsdByAnyPriceBetween(Double min, Double max, String priceName) {
        if (priceName.equals("average")) {
            return euroToUsdRepository.findEuroToUsdByAveragePriceIsBetween(min, max);
        } else if (priceName.equals("open")) {
            return euroToUsdRepository.findEuroToUsdByOpenPriceBetween(min, max);
        } else if (priceName.equals("close")) {
            return euroToUsdRepository.findEuroToUsdByClosePriceBetween(min, max);
        } else if (priceName.equals("highest")) {
            return euroToUsdRepository.findEuroToUsdByHighestPriceBetween(min, max);
        } else if (priceName.equals("lowest")) {
            return euroToUsdRepository.findEuroToUsdByLowestPriceBetween(min, max);
        }
        return euroToUsdRepository.findAll();
    }




}
