package com.ferndani00.NFTAggregator.dto.tokenDtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AmountDto {
    private String raw; //price in gwei denk ik
    private double decimal;
    private double usd;
}
