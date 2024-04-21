package com.ferndani00.NFTAggregator.models.trendingCollections;

import com.ferndani00.NFTAggregator.models.token.FloorAsk;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrendingCollection {
    private double volume;
    private int count;
    private String id;
    private String image;
    private boolean isSpam;
    private String name;
    private VolumeChange volumeChange;
    private CollectionVolume collectionVolume;
    private FloorAsk floorAsk;
}
