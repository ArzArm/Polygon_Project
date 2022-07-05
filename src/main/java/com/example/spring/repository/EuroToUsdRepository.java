package com.example.spring.repository;

import com.example.spring.entity.EuroToUsd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface EuroToUsdRepository extends JpaRepository<EuroToUsd, Integer> {

    @Query(value = "SELECT * FROM convertor.eurotousd c  WHERE c.date >= ?1 and c.date <= ?2", nativeQuery = true)
    List<EuroToUsd> findAllEuroToUsdByDate(Date from, Date to);
    List<EuroToUsd> findAll();

    List<EuroToUsd> findEuroToUsdByAveragePriceIsBetween(Double min, Double max);
    List<EuroToUsd> findEuroToUsdByClosePriceBetween(Double min, Double max);
    List<EuroToUsd> findEuroToUsdByOpenPriceBetween(Double min, Double max);
    List<EuroToUsd> findEuroToUsdByHighestPriceBetween(Double min, Double max);
    List<EuroToUsd> findEuroToUsdByLowestPriceBetween(Double min, Double max);



}
