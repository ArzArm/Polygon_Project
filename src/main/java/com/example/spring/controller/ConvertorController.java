package com.example.spring.controller;


import com.example.spring.exception.ResourceNotFoundException;
import com.example.spring.entity.EuroToUsd;
import com.example.spring.service.ConvertorService;
import com.example.spring.service.ConvertorSpecificationBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/convertor")
@ApiOperation("Convertor API")
public class ConvertorController {

    @Autowired
    private ConvertorService convertorService;


    @ApiOperation(value = "Filter EuroToUsd by period", notes = "Returns list of EuroToUsd")
    @ApiResponse(code = 404, message = "Not found any rate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "from", value = "Period start date", required = false, dataType = "Date"),
            @ApiImplicitParam(name = "to", value = "Period finish date", required = false, dataType = "Date"),
            @ApiImplicitParam(name = "flag", value = "True: data from web, false: data from DB", required = false, dataType = "boolean")
    })
    @GetMapping("/date")
    public List<EuroToUsd> findConvertorByDate(@RequestParam(required = false) Date from,
                                               @RequestParam(required = false) Date to,
                                               @RequestParam(required = false) Boolean flag) throws JsonProcessingException {
        List<EuroToUsd> euroToUsdList = convertorService.findAllByDate(from, to, flag);
        if (euroToUsdList.isEmpty()) {
            throw new ResourceNotFoundException("Not found any rate between date '" + from + "' to '" + to + "'");
        }
        return euroToUsdList;
    }
    @GetMapping("/home")
    public String getHomePage(Principal principal){
        return "Polygon Convertor HomPage " + principal.getName();
    }

    @GetMapping("/start")
    public String getStartPage(){
        return "Welcome Polygon Convertor";
    }
    @ApiOperation(value = "Update any price of EuroToUsd", notes = "Filter EuroToUsd by date")
    @ApiResponse(code = 404, message = "Not found any rate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", value = "EuroToUsd date", required = false, dataType = "Date"),
            @ApiImplicitParam(name = "priceType", value = "EuroToUsd price name", required = false, dataType = "String"),
            @ApiImplicitParam(name = "price", value = "EuroToUsd price", required = false, dataType = "double")
    })
    @PutMapping("/update")
    public void updateConvertor(@RequestParam(required = false) Date date,
                                @RequestParam(required = false) String priceType,
                                @RequestParam(required = false) Double price) throws JsonProcessingException {
        EuroToUsd euroToUsd = convertorService.findAllByDate(date, date, false).stream().findAny()
                .orElseThrow(() -> new ResourceNotFoundException("Not found any rate"));
        euroToUsd.setAveragePrice(price);
        convertorService.updateEuroToUsdPrice(euroToUsd, priceType, price);
    }

    @PostMapping("/create")
    public ResponseEntity<EuroToUsd> createConvertor(@RequestParam(required = false) Date date,
                                                     @RequestParam(required = false) Double closePrice,
                                                     @RequestParam(required = false) Double openPrice,
                                                     @RequestParam(required = false) Double highestPrice,
                                                     @RequestParam(required = false) Double lowestPrice) {
        EuroToUsd euroToUsd = new EuroToUsd();
        euroToUsd.setDate(date);
        euroToUsd.setHighestPrice(highestPrice);
        euroToUsd.setClosePrice(closePrice);
        euroToUsd.setAveragePrice((highestPrice + lowestPrice + openPrice + closePrice) / 4);
        euroToUsd.setLowestPrice(lowestPrice);
        euroToUsd.setOpenPrice(openPrice);
        convertorService.saveEuroToUsd(euroToUsd);
        return new ResponseEntity<>(euroToUsd, HttpStatus.CREATED);
    }


    /**
     * Filter EuroToUsd by any properties
     *
     * @param search - separated by a comma search parameters to insert
     *               the EuroToUsd object's properties: date, averagePrice, openPrice, closePrice
     *               operation: <, =, >
     *               value for comparison
     * @return Returns list of EuroToUsd object.
     */

    @ApiOperation(value = "Filter EuroToUsd by any properties", notes = "Returns list of EuroToUsd")
    @ApiResponse(code = 400, message = "Fail input data")
    @GetMapping(value = "/rate")
    public List<EuroToUsd> search(@RequestParam(value = "search") String search) {

        ConvertorSpecificationBuilder builder = new ConvertorSpecificationBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)([=<>])((\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]))|(\\d*(.)\\d*)),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<EuroToUsd> spec = builder.build();
        return convertorService.findBySearchCriteria(spec);

    }



}
