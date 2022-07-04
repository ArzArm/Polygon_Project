package com.example.spring.controller;


import com.example.spring.entity.EuroToUsd;
import com.example.spring.service.ConvertorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/convertor")
public class ConvertorController {

    @Autowired
    private ConvertorService convertorService;

    @GetMapping("/date")
    public List<EuroToUsd> findConvertorByDate(@RequestParam (required = false)Date from,
                                               @RequestParam (required = false) Date to,
                                               @RequestParam (required = false) Boolean flag) throws JsonProcessingException {
        return convertorService.findAllByDate(from, to, flag);
    }
}
