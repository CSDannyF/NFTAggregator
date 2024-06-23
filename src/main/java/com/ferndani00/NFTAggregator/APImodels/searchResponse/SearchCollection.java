package com.ferndani00.NFTAggregator.APImodels.searchResponse;

import com.ferndani00.NFTAggregator.APImodels.token.FloorAskPrice;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCollection {
    private int chainId;
    private String id;
    private String name;
    private String contract;
    private String image;
    private double allTimeVolume;
    private FloorAskPrice floorAskPrice;
    private String openSeaVerificationStatus;
}
