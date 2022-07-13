package com.example.spring.service;


import com.example.spring.builder.PolygonUrl;
import com.example.spring.builder.PolygonUrlBuilder;
import com.example.spring.entity.EuroToUsd;
import com.example.spring.exception.FailDataException;
import com.example.spring.repository.EuroToUsdRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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

    public void updateEuroToUsdPrice(EuroToUsd euroToUsd, String priceType, Double price) {
        RequestKeyValidation requestKeyValidation = new RequestKeyValidation();
        if (requestKeyValidation.validation(priceType)) {
            switch (priceType) {
                case "averagePrice":
                    euroToUsd.setAveragePrice(price);
                    break;
                case "openPrice":
                    euroToUsd.setOpenPrice(price);
                    break;
                case "closePrice":
                    euroToUsd.setClosePrice(price);
                    break;
                case "highestPrice":
                    euroToUsd.setHighestPrice(price);
                    break;
                case "lowestPrice":
                    euroToUsd.setLowestPrice(price);
                    break;
            }
            euroToUsdRepository.save(euroToUsd);

        }else {
            throw new FailDataException("Fail input data '" + priceType + "' ");
        }
    }

    public List<EuroToUsd> findAllByDate(Date from, Date to, boolean flag) throws JsonProcessingException {
        if (flag || from.before(polygonUrl.getFrom()) || to.after(polygonUrl.getTo())) {
            PolygonUrl newPolygonUrl = new PolygonUrlBuilder()
                    .setLimit(polygonUrl.getLimit())
                    .setTimespan(polygonUrl.getTimespan())
                    .setAdjusted(polygonUrl.getAdjusted())
                    .setCurrencyPair(polygonUrl.getCurrencyPair())
                    .setSort(polygonUrl.getSort())
                    .setMultiplier(polygonUrl.getMultiplier())
                    .setDateFrom(from)
                    .setDateTo(to).build();
            List<EuroToUsd> euroToUsdList = polygonClient.getConvertorList(newPolygonUrl);
            AtomicInteger i = new AtomicInteger(1);
            euroToUsdList.forEach(euroToUsd -> euroToUsd.setId(i.getAndIncrement()));
            return euroToUsdList;
        }

        return euroToUsdRepository.findAllEuroToUsdByDate(from, to);
    }

    public List<EuroToUsd> findBySearchCriteria(Specification<EuroToUsd> specification) {
        return euroToUsdRepository.findAll(specification);

    }
    public void saveEuroToUsd(EuroToUsd euroToUsd){
        euroToUsdRepository.save(euroToUsd);
    }




}
