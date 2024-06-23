package com.ferndani00.NFTAggregator.APImodels.trendingCollections;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TrendingCollectionResponse {
    private List<TrendingCollection> collections;
}
