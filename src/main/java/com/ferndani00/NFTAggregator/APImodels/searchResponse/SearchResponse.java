package com.ferndani00.NFTAggregator.APImodels.searchResponse;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchResponse {
    private List<SearchCollection> collections;
}
