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

    public PolygonUrl(PolygonUrlBuilder polygonUrlBuilder) {
        this.currencyPair = polygonUrlBuilder.currencyPair;
        this.multiplier = polygonUrlBuilder.multiplier;
        this.timespan = polygonUrlBuilder.timespan;
        this.from = polygonUrlBuilder.from;
        this.to = polygonUrlBuilder.to;
        this.adjusted = polygonUrlBuilder.adjusted;
        this.sort = polygonUrlBuilder.sort;
        this.limit = polygonUrlBuilder.limit;

    }


}
