package com.ferndani00.NFTAggregator.models.trendingCollections;

import com.ferndani00.NFTAggregator.models.commonClasses.FloorAsk;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class TrendingCollection {
    private double volume;
    private int count;
    private String id;
    private String image;
    private ArrayList<String> sampleImages;
    private boolean isSpam;
    private String name;
    private VolumeChange volumeChange;
    private CollectionVolume collectionVolume;
    private String banner;
    private FloorAsk floorAsk;
}
