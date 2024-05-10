package com.ferndani00.NFTAggregator.models.token;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Collection {
    private String id;
    private String name;
    private String image;
    private String slug;
    private String symbol;
    private String creator;
    private int tokenCount;
    private boolean metadataDisabled;
    private FloorAskPrice floorAskPrice;
}
