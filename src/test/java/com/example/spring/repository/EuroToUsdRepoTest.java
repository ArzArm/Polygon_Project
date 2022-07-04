package com.example.spring.repository;

import com.example.spring.entity.EuroToUsd;
import com.example.spring.service.ConvertorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
class EuroToUsdRepoTest {

    @Autowired
    ConvertorService convertorService;

    @Test
    void findById() throws JsonProcessingException {
        Pageable firstPage = PageRequest.of(0, 2);
        Date from = Date.valueOf("2021-06-22");
        Date to = Date.valueOf("2021-06-23");

        List<EuroToUsd> euroToUsdList = convertorService.findAllByDate(from, to, false);
        assertThat(euroToUsdList).hasSize(2);
        assertThat(euroToUsdList.stream().filter(euroToUsd -> euroToUsd.getDate().equals(from)).
                findAny().get().getAveragePrice()).isEqualTo(1.1909);
    }


}