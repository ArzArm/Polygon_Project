package com.example.spring.builder;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
public class PolygonUrl {
    protected String currencyPair;
    protected int multiplier;
    protected String timespan;
    protected Date from;
    protected Date to;
    protected Boolean adjusted;
    protected String sort;
    protected int limit;

}
