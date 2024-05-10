package com.ferndani00.NFTAggregator.Service;

import com.ferndani00.NFTAggregator.dto.tokenDtos.CollectionDto;

import java.util.List;

public interface NftCollectionService {
    List<CollectionDto> getAll();
    void save(CollectionDto collectionDto);
    CollectionDto getById(long id);
}
