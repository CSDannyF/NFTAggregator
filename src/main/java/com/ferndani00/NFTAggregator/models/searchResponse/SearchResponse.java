package com.ferndani00.NFTAggregator.models.searchResponse;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchResponse {
    private List<SearchCollection> collections;
}
