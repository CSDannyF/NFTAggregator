package com.ferndani00.NFTAggregator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NftDto {
    private String contract;
    private String tokenId;
    private String name;
    private String imageSmall;
    private String imageLarge;
    private String kind;
    private int rarityRank;
    private String collectionId;
    private String collectionName;
    private String priceSymbol;
    private double nativePrice;
    private double usdPrice;
    private String owner;
    private String ownerShort;
    private String externalSiteUrl;
}
