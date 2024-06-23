package com.ferndani00.NFTAggregator.APImodels.token;

import com.ferndani00.NFTAggregator.APImodels.commonClasses.Amount;
import com.ferndani00.NFTAggregator.APImodels.commonClasses.Currency;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FloorAskPrice {
    private Currency currency;
    private Amount amount;
}
