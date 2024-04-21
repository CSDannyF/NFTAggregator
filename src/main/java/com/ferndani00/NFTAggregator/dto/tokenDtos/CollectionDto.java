package com.ferndani00.NFTAggregator.dto.tokenDtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionDto {
    private String id;
    private String name;
    private String slug;
    private String symbol;
    private String creator;
    private int tokenCount;
    private int nativeCurency;
    private double floorPriceInCurrency;
    private double floorPriceInUsd;
}
