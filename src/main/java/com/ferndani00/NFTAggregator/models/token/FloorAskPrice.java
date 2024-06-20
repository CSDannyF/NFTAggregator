package com.ferndani00.NFTAggregator.models.token;

import com.ferndani00.NFTAggregator.models.commonClasses.Amount;
import com.ferndani00.NFTAggregator.models.commonClasses.Currency;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FloorAskPrice {
    private Currency currency;
    private Amount amount;
}
