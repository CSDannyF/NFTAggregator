package com.ferndani00.NFTAggregator.Endpoints;

import com.ferndani00.NFTAggregator.ApiHttpClient.ApiHttpClient;
import com.ferndani00.NFTAggregator.models.collectionResponse.CollectionResponse;
import com.ferndani00.NFTAggregator.models.trendingCollections.TrendingCollectionResponse;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class CollectionEndpoint {

    //collectionSlug is wat bij opensea achter collections/ staat in url
    public String getCollectionViaSlug(String collectionSlug) {
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
        TrendingCollectionResponse trendingCollections = gson.fromJson(response, TrendingCollectionResponse.class);
        return trendingCollections;
    }

    public CollectionResponse getCollection(String contractAddress) {
        ApiHttpClient apiHttpClient = new ApiHttpClient();
        Gson gson = new Gson();

        String url = "https://api.reservoir.tools/collections/v7?id=" + contractAddress;
        String response = apiHttpClient.getRequest(url);
        CollectionResponse collectionResponse = gson.fromJson(response, CollectionResponse.class);
        return collectionResponse;
    }
}
