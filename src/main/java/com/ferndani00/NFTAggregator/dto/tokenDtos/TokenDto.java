package com.ferndani00.NFTAggregator.dto.tokenDtos;

import com.ferndani00.NFTAggregator.models.token.Collection;
import com.ferndani00.NFTAggregator.models.token.Metadata;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDto {
    private int chainId;
    private String contract;
    private String tokenId;
    private String name;
    private String description;
    private String imageLarge;
    private int rarityRank;
    private String owner;
    private Metadata metadata;
    private Collection collection;
}
