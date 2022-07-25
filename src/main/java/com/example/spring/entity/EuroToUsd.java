package com.example.spring.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter @Setter @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Table (name = "eurotousd", schema = "convertor")
public class EuroToUsd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column (name = "averageprice", nullable = false)
    @JsonProperty("vw")
    private Double averagePrice;
    @Column (name = "openprice", nullable = false)
    @JsonProperty("o")
    private Double openPrice;
    @Column (name = "closeprice", nullable = false)
    @JsonProperty("c")
    private Double closePrice;
    @Column (name = "highestprice", nullable = false)
    @JsonProperty("h")
    private Double highestPrice;
    @Column (name = "lowestprice", nullable = false)
    @JsonProperty("l")
    private Double lowestPrice;
    @Column (name = "date", nullable = false)
    @JsonProperty("t")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "Asia/Yerevan")
    private Date date;
}
