package com.ferndani00.NFTAggregator.Service;

import com.ferndani00.NFTAggregator.Endpoints.CollectionEndpoint;
import com.ferndani00.NFTAggregator.models.trendingCollections.TrendingCollectionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class TrendingCollectionResponseServiceImpl implements TrendingCollectionResponseService {

    @Autowired
    private CollectionEndpoint collectionEndpoint = new CollectionEndpoint();

    //wordt nu enkel gebruikt in een methode van TrendingCollectionResponseServiceImpl die nergens wordt gebruikt.
    public TrendingCollectionResponse getAll(String period, int limit)
    {
        return collectionEndpoint.getTrendingCollections(period, limit);
    }


}
