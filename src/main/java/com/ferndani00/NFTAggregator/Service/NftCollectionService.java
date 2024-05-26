package com.ferndani00.NFTAggregator.Service;

import com.ferndani00.NFTAggregator.dto.NftCollectionDto;
import com.ferndani00.NFTAggregator.dto.tokenDtos.CollectionDto;
import com.ferndani00.NFTAggregator.models.databaseModels.NftCollection;

import java.util.List;

public interface NftCollectionService {
    List<CollectionDto> getAll();
    List<NftCollection> getAllModel();
    void save(CollectionDto collectionDto);
    void saveAll(List<NftCollection> nftCollections);
    CollectionDto getById(long id);

    NftCollectionDto getByName(String name);
}
