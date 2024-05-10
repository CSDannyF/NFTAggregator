package com.ferndani00.NFTAggregator.Service;

import com.ferndani00.NFTAggregator.models.trendingCollections.TrendingCollectionResponse;

public interface CollectionService {

    TrendingCollectionResponse getAll(String period, int limit);
}
