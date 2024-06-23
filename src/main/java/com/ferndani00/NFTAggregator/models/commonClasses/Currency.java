package com.ferndani00.NFTAggregator.models.commonClasses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Currency {
    private String contract;
    private String name;
    private String symbol;
    private int decimals;
}
