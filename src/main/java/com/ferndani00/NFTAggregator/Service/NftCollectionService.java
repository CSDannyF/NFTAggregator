package com.ferndani00.NFTAggregator.Service;

import com.ferndani00.NFTAggregator.dto.NftCollectionDto;
import com.ferndani00.NFTAggregator.models.databaseModels.NftCollection;

import java.util.List;

public interface NftCollectionService {
    List<NftCollectionDto> getAll();
    List<NftCollection> getAllModel();
    void save(NftCollectionDto nftCollectionDto);
    void saveAll(List<NftCollection> nftCollections);
    NftCollectionDto getCollection(String contractAddress);

    List<NftCollectionDto> getCollectionsByName(String name);
}
