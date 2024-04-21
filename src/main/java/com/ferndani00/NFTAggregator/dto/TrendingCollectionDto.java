package com.ferndani00.NFTAggregator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrendingCollectionDto {
    private String name;
    private String image;
    private double oneDayVolumeChange;
    private double oneDayCollectionVolume;
    private String priceSymbol;
    private double nativePrice;
}
