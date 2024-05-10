package com.ferndani00.NFTAggregator.Service;

import com.ferndani00.NFTAggregator.dto.NftDto;

import java.util.List;

public interface NftService {
    List<NftDto> getAll();
    void save(NftDto nftDto);
    NftDto getById(long id);
}
