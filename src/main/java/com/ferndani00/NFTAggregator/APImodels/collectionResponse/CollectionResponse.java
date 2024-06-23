package com.ferndani00.NFTAggregator.APImodels.collectionResponse;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CollectionResponse {
    private List<Collection> collections;
}
