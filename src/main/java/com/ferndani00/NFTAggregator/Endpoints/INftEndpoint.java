package com.ferndani00.NFTAggregator.Endpoints;

import com.ferndani00.NFTAggregator.models.databaseModels.Nft;

import java.util.List;

public interface INftEndpoint {
    Nft getNFT(String contractAddress, String tokenId);

    List<Nft> getCollection(String collectionSlug);
}
