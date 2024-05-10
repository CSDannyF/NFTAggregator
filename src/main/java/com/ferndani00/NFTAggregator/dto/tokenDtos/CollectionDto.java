package com.ferndani00.NFTAggregator.dto.tokenDtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionDto {
    private String contractAddress;
    private String name;
    private String slug;
    private String description;
    private String image;
    private String banner;
    private String twitterUrl;
    private String symbol;
    private String creator;
    private int tokenCount;
    private int onSaleCount;
    private int ownerCount;
    private String nativeCurrency;
    private double floorPriceInCurrency;
    private double floorPriceInUsd;
    private double totalVolume;
    private double dailyVolume;
}
