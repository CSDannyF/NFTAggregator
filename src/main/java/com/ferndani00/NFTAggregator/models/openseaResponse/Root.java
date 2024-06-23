package com.ferndani00.NFTAggregator.models.openseaResponse;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Root {
    private List<OpenseaCollection> collections;
    private String next;
}
