package com.ferndani00.NFTAggregator.models.commonClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
    private String contract;
    private String name;
    private String symbol;
    private int decimals;
}
