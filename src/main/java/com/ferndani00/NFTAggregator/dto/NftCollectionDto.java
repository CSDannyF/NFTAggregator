package com.ferndani00.NFTAggregator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NftCollectionDto {
    private int chainId;
    private String contractAddress;
    private String name;
    private String slug;
    private String description;
    private String image;
    private String banner;
    private String twitterUrl;
    private String symbol;
    private String creator;
    private int ownerCount;
    private int tokenCount;
    private int onSaleCount;
    private String nativePriceSymbol;
    private double nativeFloorPrice;
    private double allTimeVolume;
    private double dailyVolume;
    private double dailyVolumeChange;
    private double floorPriceInUsd;

}
