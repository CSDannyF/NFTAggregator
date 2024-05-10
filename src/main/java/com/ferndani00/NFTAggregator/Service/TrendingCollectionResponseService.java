package com.ferndani00.NFTAggregator.Service;

import com.ferndani00.NFTAggregator.models.trendingCollections.TrendingCollectionResponse;

public interface TrendingCollectionResponseService {
    TrendingCollectionResponse getAll(String period, int limit);
}
