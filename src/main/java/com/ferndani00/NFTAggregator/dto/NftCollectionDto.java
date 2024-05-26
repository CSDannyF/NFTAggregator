package com.ferndani00.NFTAggregator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NftCollectionDto {
    private String name;
    private String image;
    private String priceSymbol;
    private String contractAddress;
    private double nativePrice;
    private String slug;
}
