package com.ferndani00.NFTAggregator.models.token;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token {
    private int chainId;
    private String contract;
    private String tokenId;
    private String name;
    private String description;
    private String image;
    private String imageSmall;
    private String imageLarge;
    private Metadata metadata;
    private String media;
    private String kind;
    private boolean metadataDisabled;
    private String supply;
    private String remainingSupply;
    private String decimals;
    private double rarity;
    private int rarityRank;
    private Collection collection;
    private String owner;
}
