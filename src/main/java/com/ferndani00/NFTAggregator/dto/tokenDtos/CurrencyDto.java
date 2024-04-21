package com.ferndani00.NFTAggregator.dto.tokenDtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyDto {
    private String contract;
    private String name;
    private String symbol;
}
