package com.ferndani00.NFTAggregator.models.commonClasses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Price {
    private Currency currency;
    private Amount amount;
}
