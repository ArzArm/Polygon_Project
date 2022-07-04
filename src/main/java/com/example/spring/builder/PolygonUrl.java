package com.example.spring.builder;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
public class PolygonUrl {
    private String currencyPair;
    private int multiplier;
    private String timespan;
    private Date from;
    private Date to;
    private Boolean adjusted;
    private String sort;
    private int limit;

    protected PolygonUrl(String currencyPair, int multiplier, String timespan, Date from, Date to, Boolean adjusted, String sort, int limit) {
        this.currencyPair = currencyPair;
        this.multiplier = multiplier;
        this.timespan = timespan;
        this.from = from;
        this.to = to;
        this.adjusted = adjusted;
        this.sort = sort;
        this.limit = limit;
    }

}
