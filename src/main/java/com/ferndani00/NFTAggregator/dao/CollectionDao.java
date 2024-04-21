package com.ferndani00.NFTAggregator.dao;

import com.ferndani00.NFTAggregator.ApiHttpClient.ApiHttpClient;
import com.ferndani00.NFTAggregator.models.trendingCollections.TrendingCollectionResponse;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class CollectionDao {

    //collectionSlug is wat bij opensea achter collections/ staat in url
    public String getCollection(String collectionSlug) {
        ApiHttpClient apiHttpClient = new ApiHttpClient();
        Gson gson = new Gson();

        String url = "https://api.opensea.io/api/v2/collections/" + collectionSlug;
        String response = apiHttpClient.getRequest(url);
        return response;
    }

    //reservoir trending collections
    public TrendingCollectionResponse getTrendingCollections(String period, int limit) {
        ApiHttpClient apiHttpClient = new ApiHttpClient();
        Gson gson = new Gson();
        String url = "https://api.reservoir.tools/collections/trending/v1?period=" + period + "&limit=" + limit + "&sortBy=volume";

        String response = apiHttpClient.getRequest(url);
        TrendingCollectionResponse TrendingCollectionResponse = gson.fromJson(response, com.ferndani00.NFTAggregator.models.trendingCollections.TrendingCollectionResponse.class);
        return TrendingCollectionResponse;
    }
}
