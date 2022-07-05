package com.example.spring.builder;

import java.sql.Date;

public class PolygonUrlBuilder {
    protected String currencyPair;
    protected int multiplier;
    protected String timespan;
    protected Date from;
    protected Date to;
    protected Boolean adjusted;
    protected String sort;
    protected int limit;


    public PolygonUrlBuilder setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
        return this;
    }

    public PolygonUrlBuilder setMultiplier(Integer multiplier) {
        this.multiplier = multiplier;
        return this;
    }

    public PolygonUrlBuilder setTimespan(String timespan) {
        this.timespan = timespan;
        return this;
    }

    public PolygonUrlBuilder setDateFrom(Date from) {
        this.from = from;
        return this;
    }

    public PolygonUrlBuilder setDateTo(Date to) {
        this.to = to;
        return this;
    }


    public PolygonUrlBuilder setAdjusted(Boolean adjusted) {
        this.adjusted = adjusted;
        return this;
    }

    public PolygonUrlBuilder setSort(String sort) {
        this.sort = sort;
        return this;
    }

    public PolygonUrlBuilder setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public PolygonUrl build() {
        return new PolygonUrl(this);
    }

}
