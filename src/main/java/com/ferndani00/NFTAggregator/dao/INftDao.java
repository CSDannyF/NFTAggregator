package com.ferndani00.NFTAggregator.dao;

import com.ferndani00.NFTAggregator.models.Nft;

import java.util.List;

public interface INftDao {
    Nft getNFT(String contractAddress, String tokenId);

    List<Nft> getCollection(String collectionSlug);
}
