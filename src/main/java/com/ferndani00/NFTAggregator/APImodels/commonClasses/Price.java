package com.ferndani00.NFTAggregator.APImodels.commonClasses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Price {
    private Currency currency;
    private Amount amount;
}
