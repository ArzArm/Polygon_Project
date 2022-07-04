package com.example.spring.builder;

import java.sql.Date;

public class PolygonUrlBuilder {
    private String currencyPair;
    private int multiplier;
    private String timespan;
    private Date from;
    private Date to;
    private Boolean adjusted;
    private String sort;
    private int limit;


    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    public void setMultiplier(Integer multiplier) {
        this.multiplier = multiplier;
    }

    public void setTimespan(String timespan) {
        this.timespan = timespan;
    }

    public void setDateFrom(Date from) {
        this.from = from;
    }

    public void setDateTo(Date to) {
        this.to = to;
    }


    public void setAdjusted(Boolean adjusted) {
        this.adjusted = adjusted;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public PolygonUrl getResult() {
        return new PolygonUrl(currencyPair, multiplier, timespan, from, to, adjusted, sort, limit);
    }
}
