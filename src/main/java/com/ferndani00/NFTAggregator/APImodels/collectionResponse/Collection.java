package com.ferndani00.NFTAggregator.APImodels.collectionResponse;

import com.ferndani00.NFTAggregator.APImodels.commonClasses.FloorAsk;
import com.ferndani00.NFTAggregator.APImodels.trendingCollections.VolumeChange;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Collection {
    private int chainId;
    private String id;
    private String slug;
    private String name;
    private String symbol;
    private String image;
    private String banner;
    private String twitterUrl;
    private String description;
    private int tokenCount;
    private int onSaleCount;
    private String creator;
    private FloorAsk floorAsk;
    private Volume volume;
    private VolumeChange volumeChange;
    private int ownerCount;
}
