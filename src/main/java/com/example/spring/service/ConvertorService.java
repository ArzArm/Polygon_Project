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

    PolygonUrlBuilder builder = new PolygonUrlBuilder();

    public void createPolygonUrl(PolygonUrlBuilder builder){
        builder.setCurrencyPair("C:EURUSD");
        builder.setMultiplier(1);
        builder.setTimespan("day");
        builder.setDateFrom(Date.valueOf("2021-06-22"));
        builder.setDateTo(Date.valueOf("2021-07-22"));
        builder.setAdjusted(true);
        builder.setSort("asc");
        builder.setLimit(120);
    }

    public void saveAllEuroToUsd () throws JsonProcessingException {
        createPolygonUrl(builder);
        euroToUsdRepository.saveAll(polygonClient.getConvertorList( builder.getResult()));
    }

    public List<EuroToUsd> findAllByDate(Date from, Date to, boolean flag) throws JsonProcessingException {
        createPolygonUrl(builder);
        PolygonUrl polygonUrl = builder.getResult();

        if (flag || from.before(polygonUrl.getFrom()) || to.after(polygonUrl.getTo())){
            polygonUrl.setFrom(from);
            polygonUrl.setTo(to);
            List<EuroToUsd> euroToUsdList = polygonClient.getConvertorList(polygonUrl);
            AtomicInteger i = new AtomicInteger(1);
            euroToUsdList.forEach(euroToUsd -> euroToUsd.setId(i.getAndIncrement()));
            return euroToUsdList;
        }

        return euroToUsdRepository.findAllEuroToUsd(from, to);
    }

}
